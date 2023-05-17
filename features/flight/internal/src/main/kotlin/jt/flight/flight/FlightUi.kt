package jt.flight.flight

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.ui.Ui
import javax.inject.Inject

class FlightUi : Ui<FlightScreen.CounterState> {
    @Composable
    override fun Content(state: FlightScreen.CounterState, modifier: Modifier) {
        Box {
            Text(state.title)
        }
    }
}

class FlightUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when(screen) {
            is jt.flight.flight.FlightScreen -> FlightUi()
            else -> null
        }
    }
}
