package jt.flight.home

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
class HomeScreen : Screen {
    data class State(
        val title: String,
        val initialFlightNumber: String,
        val eventSink: (Events) -> Unit
    ) : CircuitUiState

    sealed class Events {
        data class FlightNumberUpdated(
            val flightNumber: String,
        ) : Events()
    }
}
