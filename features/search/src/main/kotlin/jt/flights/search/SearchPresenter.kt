package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
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

public class SearchPresenter internal constructor(
	private val searchResultsForFlightNumber: SearchResultsForFlightNumber,
	private val searchHistoryForTerm: SearchHistoryForTerm,
	private val allSearchHistory: AllSearchHistory,
	private val saveSearchTerm: SaveSearchTerm,
) : Presenter<SearchScreen.UiState> {
	@Composable
	override fun present(): SearchScreen.UiState {
		var nonSearchFlight by rememberRetained { mutableStateOf(SearchTerm("")) }
		var flightNumber by rememberRetained { mutableStateOf(SearchTerm("")) }
		var presentation by rememberRetained {
			mutableStateOf<FlightPresentation>(FlightPresentation.Loaded(FlightResults.JustSearch))
		}
		var search by rememberRetained { mutableStateOf<List<SearchTerm>>(emptyList()) }

		if (flightNumber.value.isNotEmpty()) {
			LaunchedEffect(flightNumber) {
				launch {
					saveSearchTerm(flightNumber)
				}
				presentation = FlightPresentation.Loading
				val searchResult = searchResultsForFlightNumber(searchTerm = flightNumber)
				presentation = FlightPresentation.Loaded(searchResult)
			}
		} else {
			presentation = FlightPresentation.Loaded(
				flightResults = FlightResults.JustSearch
			)
		}

		LaunchedEffect(nonSearchFlight) {
			search = if (nonSearchFlight.isEmpty()) {
				allSearchHistory(take = 10)
			} else {
				searchHistoryForTerm(nonSearchFlight)
			}
		}

		return SearchScreen.UiState(
			searchResults = search,
			presentation = presentation,
		) { event ->
			when (event) {
				is SearchScreen.Event.Search -> {
					flightNumber = event.query
				}

				is SearchScreen.Event.SearchChange -> {
					nonSearchFlight = event.query
				}
			}
		}
	}

	@Stable
	public sealed interface FlightPresentation {
		public data object Loading: FlightPresentation

		@JvmInline
		public value class Loaded(
			public val flightResults: FlightResults
		): FlightPresentation
	}
}
