package jt.flight.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.ui.Ui
import jt.flight.home.implementation.R
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
class HomeUi : Ui<HomeScreen.State> {
    @Composable
    override fun Content(state: HomeScreen.State, modifier: Modifier) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            var searchText by remember { mutableStateOf(TextFieldValue(state.initialFlightNumber)) }
            TextField(
                value = searchText,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.home_flight_number))
                },
                onValueChange = { updatedFlightNumber ->
                    searchText = updatedFlightNumber
                    state.eventSink(HomeScreen.Events.FlightNumberUpdated(updatedFlightNumber.text))
                }
            )
        }
    }
}

class HomeUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            is HomeScreen -> HomeUi()
            else -> null
        }
    }
}
