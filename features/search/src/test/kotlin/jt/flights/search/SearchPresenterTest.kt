package jt.flights.search

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.slack.circuit.test.test
import jt.flights.Searches
import jt.flights.networking.Network
import jt.flights.networking.OkHttpNetwork
import jt.flights.search.usecases.AllSearchHistory
import jt.flights.search.usecases.SaveSearchTerm
import jt.flights.search.usecases.SearchHistoryForTerm
import jt.flights.search.usecases.SearchResultsForFlightNumber
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import kotlin.test.AfterTest
import kotlin.test.Test

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
	fun `test search term history`() = runTest {
		searches.searchQueries.insert("BA1234")
		searches.searchQueries.insert("BA1232")
		searches.searchQueries.insert("UAL1")
		searchPresenter.test {
			val firstState = awaitError()
			firstState.printStackTrace()
//			firstState.eventSink(SearchScreen.Event.SearchChange(SearchTerm("BA")))
//			val secondState = awaitItem()
//			assertEquals(2, secondState.searchResults.size)
		}
	}
}