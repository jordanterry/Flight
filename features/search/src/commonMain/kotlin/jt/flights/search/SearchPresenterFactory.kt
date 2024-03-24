package jt.flights.search

import app.cash.sqldelight.db.SqlDriver
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import jt.flights.Searches
import jt.flights.networking.Network
import jt.flights.search.usecases.AllSearchHistory
import jt.flights.search.usecases.SaveSearchTerm
import jt.flights.search.usecases.SearchHistoryForTerm
import jt.flights.search.usecases.SearchResultsForFlightNumber

public class SearchPresenterFactory(
	private val flightAwareBaseUrl: String,
	private val network: Network,
	private val sqlDriver: SqlDriver,
) : Presenter.Factory {
	override fun create(
		screen: Screen,
		navigator: Navigator,
		context: CircuitContext
	): Presenter<*>? {
		return when (screen) {
			is SearchScreen -> createSearchPresenter()
			else -> null
		}
	}

	private fun createSearchPresenter(): SearchPresenter {
		val searchHistoryRepository = SearchHistoryRepository(Searches(sqlDriver))
		val searchDataSource = NetworkSearchDataSource(
			flightAwareBaseUrl = flightAwareBaseUrl,
			network = network,
		)
		val searchRepository = SearchRepositoryImpl(searchDataSource)
		return SearchPresenter(
			searchResultsForFlightNumber = SearchResultsForFlightNumber(
				searchRepository = searchRepository
			),
			searchHistoryForTerm = SearchHistoryForTerm(searchHistoryRepository),
			allSearchHistory = AllSearchHistory(searchHistoryRepository),
			saveSearchTerm = SaveSearchTerm(searchHistoryRepository),
		)
	}
}