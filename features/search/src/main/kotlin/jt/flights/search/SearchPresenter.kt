package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.coroutines.launch

public class SearchPresenter(
	private val searchResultsForFlightNumber: SearchResultsForFlightNumber,
	private val searchHistoryRepository: SearchHistoryRepositoryImpl,
) : Presenter<SearchScreen.UiState> {
	@Composable
	override fun present(): SearchScreen.UiState {
		var nonSearchFlight by rememberRetained { mutableStateOf(SearchTerm("")) }
		var flightNumber by rememberRetained { mutableStateOf(SearchTerm("")) }
		var presentation by rememberRetained {
			mutableStateOf<FlightPresentation>(FlightPresentation.Loaded(SearchResultsForFlightNumber.FlightResults.JustSearch))
		}
		var search by rememberRetained { mutableStateOf<List<SearchTerm>>(emptyList()) }

		if (flightNumber.value.isNotEmpty()) {
			LaunchedEffect(flightNumber) {
				launch {
					searchHistoryRepository.save(flightNumber)
				}
				presentation = FlightPresentation.Loading
				val searchResult = searchResultsForFlightNumber.search(flightNumber = flightNumber)
				presentation = FlightPresentation.Loaded(searchResult)
			}
		} else {
			presentation = FlightPresentation.Loaded(
				flightResults = SearchResultsForFlightNumber.FlightResults.JustSearch
			)
		}

		LaunchedEffect(nonSearchFlight) {
			search = searchHistoryRepository.get(nonSearchFlight)
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
			public val flightResults: SearchResultsForFlightNumber.FlightResults
		): FlightPresentation
	}
}
