package jt.flights.search

import androidx.compose.runtime.Stable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
public data object SearchScreen : Screen {
	@Stable
	public data class UiState(
		val searchResults: List<SearchTerm>,
		val presentation: SearchPresenter.FlightPresentation,
		val eventSink: (Event) -> Unit,
	) : CircuitUiState

	public sealed interface Event : CircuitUiEvent {
		public data class Search(
			val query: SearchTerm,
		) : Event
		public data class SearchChange(
			val query: SearchTerm,
		) : Event
	}
}