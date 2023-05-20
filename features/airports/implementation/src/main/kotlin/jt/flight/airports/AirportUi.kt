package jt.flight.airports

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.ui.Ui
import javax.inject.Inject

class FlightUi : Ui<AirportScreen.AirportState> {
    @Composable
    override fun Content(state: AirportScreen.AirportState, modifier: Modifier) {
        Box {
            Text(state.title)
        }
    }
}

class FlightUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when(screen) {
            is AirportScreen -> FlightUi()
            else -> null
        }
    }
}
