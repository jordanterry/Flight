package jt.flights.search

import SIA_RESPONSE
import app.cash.paparazzi.Paparazzi
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.slack.circuit.test.test
import jt.flights.Searches
import jt.flights.model.Flight
import jt.flights.networking.Network
import jt.flights.networking.OkHttpNetwork
import jt.flights.search.usecases.AllSearchHistory
import jt.flights.search.usecases.FlightResults
import jt.flights.search.usecases.SearchHistoryForTerm
import jt.flights.search.usecases.SaveSearchTerm
import jt.flights.search.usecases.SearchResultsForFlightNumber
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class SearchPresenterTest {

	@get:Rule
	val paparazzi = Paparazzi()

	private val mockWebServer: MockWebServer = MockWebServer().apply {
		start()
	}
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
			val firstState = awaitItem()
			firstState.eventSink(SearchScreen.Event.SearchChange(SearchTerm("BA")))
			assertEquals(0, firstState.searchResults.size)
			assertEquals(0, awaitItem().searchResults.size) // recompose
			assertEquals(2, awaitItem().searchResults.size) // results loaded
		}
	}

	@Test
	fun `search terms are not returned when no saved searches`() = runTest {
		searchPresenter.test {
			val firstState = awaitItem()
			firstState.eventSink(SearchScreen.Event.SearchChange(SearchTerm("BA")))
			assertEquals(0, firstState.searchResults.size)
			assertEquals(0, awaitItem().searchResults.size) // recompose
		}
	}

	@Test
	fun `search term is saved when searching`() = runTest {
		searchPresenter.test {
			val firstState = awaitItem()
			firstState.eventSink(SearchScreen.Event.Search(SearchTerm("BA1234")))
			assertEquals(0, firstState.searchResults.size)
			cancelAndConsumeRemainingEvents()
			val search = searches.searchQueries.selectAll().executeAsOne()
			assertEquals("BA1234", search.search)
		}
	}


	@Test
	fun `saved searches are returned when searching`() = runTest {
		searchPresenter.test {
			val firstState = awaitItem()
			firstState.eventSink(SearchScreen.Event.Search(SearchTerm("BA1234")))
			assertEquals(0, firstState.searchResults.size)
			cancelAndConsumeRemainingEvents()
			val search = searches.searchQueries.selectAll().executeAsOne()
			assertEquals("BA1234", search.search)
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
			val firstState = awaitItem()
			firstState.eventSink(SearchScreen.Event.Search(SearchTerm("SIA321")))
			assertIs<SearchPresenter.FlightPresentation.Loaded>(
				awaitItem().presentation
			)
			assertEquals(SearchPresenter.FlightPresentation.Loading, awaitItem().presentation)
			val result = awaitItem()
			val presentation = result.presentation
			assertTrue(presentation is SearchPresenter.FlightPresentation.Loaded)
			val flightResults = presentation.flightResults
			assertTrue(flightResults is FlightResults.ActiveFlightFound)
			assertEquals(Flight.Id("SIA321"), flightResults.flight.id)
			assertEquals(true, flightResults.flight.isActive)
		}
	}
}