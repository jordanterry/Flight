package jt.flights.search

import androidx.compose.runtime.Stable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import jt.flights.model.Flight
import kotlinx.parcelize.Parcelize

@Parcelize
public data object SearchScreen : Screen {
	@Stable
	public data class UiState(
		val presentation: SearchPresenter.FlightPresentation,
		val eventSink: (Event) -> Unit,
	) : CircuitUiState

	public sealed interface Event : CircuitUiEvent {
		public data class Search(
			val query: String,
		) : Event
	}
}