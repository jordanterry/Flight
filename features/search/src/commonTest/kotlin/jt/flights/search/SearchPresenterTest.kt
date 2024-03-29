package jt.flights.search

import SIA_RESPONSE
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.slack.circuit.test.test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import jt.flights.Searches
import jt.flights.model.Flight
import jt.flights.networking.Network
import jt.flights.networking.OkHttpNetwork
import jt.flights.search.usecases.AllSearchHistory
import jt.flights.search.usecases.SearchHistoryForTerm
import jt.flights.search.usecases.SaveSearchTerm
import jt.flights.search.usecases.SearchResultsForFlightNumber
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

class SearchPresenterTest {

	private val mockWebServer = MockWebServer()
	private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, schema = Searches.Schema)
	private val searches = Searches(driver)
	private val searchHistoryRepository = SearchHistoryRepository(searches)
	private val network: Network = OkHttpNetwork()

	private val searchDataSource = NetworkSearchDataSource(
		mockWebServer.url("").toString(),
		network,
	)
	private val searchRepository = SearchRepositoryImpl(searchDataSource)

	private val searchPresenter = SearchPresenter(
		searchResultsForFlightNumber = SearchResultsForFlightNumber(searchRepository),
		searchHistoryForTerm = SearchHistoryForTerm(searchHistoryRepository),
		allSearchHistory = AllSearchHistory(searchHistoryRepository),
		saveSearchTerm = SaveSearchTerm(searchHistoryRepository)
	)

	@After
	fun after() {
		mockWebServer.shutdown()
	}

	@Test
	fun `matching search terms are returned `() = runTest {
		searches.searchQueries.insert("BA1234")
		searches.searchQueries.insert("BA1232")
		searches.searchQueries.insert("UAL1")
		searchPresenter.test {
			// Initial item
			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.also { it.eventSink(SearchScreen.Event.SearchChange(SearchTerm("BA"))) }
				.pastSearchTerms
				.shouldBeEmpty()

			// Recomposition after event
			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.pastSearchTerms
				.shouldBeEmpty()

			// Search results
			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.pastSearchTerms
				.shouldHaveSize(2)
		}
	}

	@Test
	fun `search terms are not returned when no saved searches`() = runTest {
		searchPresenter.test {
			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.also { it.eventSink(SearchScreen.Event.SearchChange(SearchTerm("BA"))) }
				.pastSearchTerms
				.shouldBeEmpty()

			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.pastSearchTerms
				.shouldBeEmpty()
		}
	}

	@Test
	fun `search term is saved when searching`() = runTest {
		// Searching makes a network request!
		// We don't want that in this test.
		// But it is fine for now.
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(
					SIA_RESPONSE
				)
		)
		searchPresenter.test {
			// Initial screen and initial state
			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.also { it.eventSink(SearchScreen.Event.Search(SearchTerm("BA1234"))) }
				.pastSearchTerms
				.shouldBeEmpty()

			// Recomposition from save
			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.pastSearchTerms
				.shouldBeEmpty()

			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.content
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded.Content.Initial>()

			searches.searchQueries
				.selectAll()
				.executeAsList()
				.shouldHaveSingleElement { search -> search.search == "BA1234" }
		}
	}

	@Test
	fun `flights are returned`() = runTest {
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(
					SIA_RESPONSE
				)
		)
		searchPresenter.test {

			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.also { it.eventSink(SearchScreen.Event.Search(SearchTerm("SIA321"))) }
				.content
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded.Content.Initial>()

			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.content
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded.Content.Initial>()

			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()

			awaitItem()
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded>()
				.content
				.shouldBeInstanceOf<SearchScreen.UiState.Loaded.Content.Found>()
				.flight
				.should { flight ->
					flight.id.shouldBe(Flight.Id("SIA321"))
					flight.isActive.shouldBeTrue()
				}

		}
	}
}