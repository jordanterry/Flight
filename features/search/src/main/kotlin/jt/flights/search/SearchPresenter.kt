package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter

public class SearchPresenter(
	private val searchResultsForFlightNumber: SearchResultsForFlightNumber,
) : Presenter<SearchScreen.UiState> {
	@Composable
	override fun present(): SearchScreen.UiState {
		var flightNumber by rememberRetained { mutableStateOf("") }
		var presentation by rememberRetained {
			mutableStateOf<FlightPresentation>(FlightPresentation.Loaded(SearchResultsForFlightNumber.FlightResults.JustSearch))
		}
		if (flightNumber.isNotEmpty()) {
			LaunchedEffect(flightNumber) {
				presentation = FlightPresentation.Loading
				val searchResult = searchResultsForFlightNumber.search(flightNumber = flightNumber)
				presentation = FlightPresentation.Loaded(searchResult)
			}
		}

		return SearchScreen.UiState(
			presentation = presentation,
		) { event ->
			when (event) {
				is SearchScreen.Event.Search -> {
					flightNumber = event.query
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
