package jt.flights.search

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import jt.flights.model.Flight
import kotlinx.parcelize.Parcelize

@Parcelize
data object SearchScreen : Screen {
	data class UiState(
		val searchResults: List<Flight>,
		val eventSink: (Event) -> Unit,
	) : CircuitUiState

	sealed interface Event : CircuitUiEvent {
		data class Search(
			val query: String,
		) : Event
	}
}