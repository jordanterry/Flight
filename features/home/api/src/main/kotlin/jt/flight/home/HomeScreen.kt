package jt.flight.home

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
class HomeScreen : Screen {
    data class HomeState(
        val title: String,
    ) : CircuitUiState
}
