package jt.flight.flight

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
class FlightScreen : Screen {
    data class CounterState(
        val title: String,
    ) : CircuitUiState
}
