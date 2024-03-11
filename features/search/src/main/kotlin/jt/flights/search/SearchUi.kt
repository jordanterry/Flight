package jt.flights.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.squareup.anvil.annotations.ContributesMultibinding
import jt.flights.di.AppScope
import javax.inject.Inject

class SearchUi : Ui<SearchScreen.UiState> {
    @Composable
    override fun Content(
        state: SearchScreen.UiState,
        modifier: Modifier
    ) {
        Search(state, modifier)
    }
}

@ContributesMultibinding(AppScope::class, Ui.Factory::class)
class SearchUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            is SearchScreen -> SearchUi()
            else -> null
        }
    }
}

@Composable
fun Search(state: SearchScreen.UiState, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row{
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Enter text") }
            )
            Button(onClick = {
                state.eventSink(
                    SearchScreen.Event.Search(text)
                )
            }) {
                Text(text = "Search")
            }
        }

        LazyColumn {
            items(state.searchResults) { flight ->
                FlightCard(flight)
            }
        }
    }
}