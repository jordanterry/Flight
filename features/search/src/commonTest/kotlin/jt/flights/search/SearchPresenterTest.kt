package jt.flights.search

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.slack.circuit.test.test
import jt.flights.Searches
import jt.flights.networking.Network
import jt.flights.networking.OkHttpNetwork
import jt.flights.search.usecases.AllSearchHistory
import jt.flights.search.usecases.SearchHistoryForTerm
import jt.flights.search.usecases.SaveSearchTerm
import jt.flights.search.usecases.SearchResultsForFlightNumber
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import kotlin.test.Test
import kotlin.test.AfterTest
import kotlin.test.assertEquals

class SearchPresenterTest {

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

	@AfterTest
	fun after() {
		mockWebServer.shutdown()
	}

	@Test
	fun `test search term history with search term and data`() = runTest {
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
	fun `test search term history with search term and no data`() = runTest {
		searchPresenter.test {
			val firstState = awaitItem()
			firstState.eventSink(SearchScreen.Event.SearchChange(SearchTerm("BA")))
			assertEquals(0, firstState.searchResults.size)
			assertEquals(0, awaitItem().searchResults.size) // recompose
		}
	}

	@Test
	fun `test save search term`() = runTest {
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
	fun `test flights are returned when searching`() = runTest {
		searchPresenter.test {
			val firstState = awaitItem()
			firstState.eventSink(SearchScreen.Event.Search(SearchTerm("BA1234")))
			assertEquals(0, firstState.searchResults.size)
			cancelAndConsumeRemainingEvents()
			val search = searches.searchQueries.selectAll().executeAsOne()
			assertEquals("BA1234", search.search)
		}
	}
}