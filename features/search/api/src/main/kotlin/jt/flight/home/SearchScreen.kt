package jt.flight.home

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
class SearchScreen : Screen {
    data class SearchState(
        val title: String,
    ) : CircuitUiState
}
