package jt.flight.airports

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
class AirportScreen : Screen {
    data class AirportState(
        val title: String,
    ) : CircuitUiState
}
