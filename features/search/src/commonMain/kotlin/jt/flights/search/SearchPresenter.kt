package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import jt.flights.search.usecases.AllSearchHistory
import jt.flights.search.usecases.FlightResults
import jt.flights.search.usecases.SaveSearchTerm
import jt.flights.search.usecases.SearchHistoryForTerm
import jt.flights.search.usecases.SearchResultsForFlightNumber
import kotlinx.coroutines.launch

internal class SearchPresenter internal constructor(
	private val searchResultsForFlightNumber: SearchResultsForFlightNumber,
	private val searchHistoryForTerm: SearchHistoryForTerm,
	private val allSearchHistory: AllSearchHistory,
	private val saveSearchTerm: SaveSearchTerm,
) : Presenter<SearchScreen.UiState> {

	@Composable
	override fun present(): SearchScreen.UiState {
		var searchOpen by rememberRetained { mutableStateOf(true) }
		var currentSearchTerm by rememberRetained { mutableStateOf<SearchTerm?>(null) }
		var flightNumber by rememberRetained { mutableStateOf(SearchTerm("")) }
		var pastSearchTerms by rememberRetained { mutableStateOf<List<SearchTerm>>(emptyList()) }
		var searchResult by rememberRetained { mutableStateOf<FlightResults?>(null) }

		LaunchedEffect(flightNumber) {
			if (!flightNumber.isEmpty()) {
				launch { saveSearchTerm(flightNumber) }
				searchResult = searchResultsForFlightNumber(searchTerm = flightNumber)
			}
		}
		val searchTerm = currentSearchTerm
		LaunchedEffect(searchTerm) {
			pastSearchTerms = searchHistory(searchTerm)
		}
		val eventSink: (SearchScreen.Event) -> Unit = { event ->
			when (event) {
				is SearchScreen.Event.Search -> {
					flightNumber = event.query
					searchOpen = false
				}
				is SearchScreen.Event.SearchChange -> currentSearchTerm = event.query
				SearchScreen.Event.CloseSearch -> searchOpen = false
				SearchScreen.Event.OpenSearch -> searchOpen = true
			}
		}
		return SearchScreen.UiState.Loaded(
			hint = "What's your flight number?",
			search = currentSearchTerm ?: SearchTerm.empty,
			content = createContent(flightNumber, searchResult),
			eventSink = eventSink,
			pastSearchTerms = pastSearchTerms,
			searching = searchOpen,
		)
	}

	private fun createContent(
		flightNumber: SearchTerm,
		searchResult: FlightResults? = null,
	): SearchScreen.UiState.Loaded.Content {
		return when (searchResult) {
			is FlightResults.ActiveFlightFound -> SearchScreen.UiState.Loaded.Content.Found(
				searchResult.flight
			)
			FlightResults.NoResultsFound -> SearchScreen.UiState.Loaded.Content.NoResults("No results")
			null -> SearchScreen.UiState.Loaded.Content.Initial
		}
	}

	private suspend fun searchHistory(searchTerm: SearchTerm?): List<SearchTerm> {
		return if (searchTerm != null) {
			if (searchTerm.isEmpty()) {
				allSearchHistory(take = 10)
			} else {
				searchHistoryForTerm(searchTerm)
			}
		} else {
			emptyList()
		}
	}
}
