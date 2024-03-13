package jt.flights.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(state: SearchScreen.UiState, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            var text by remember { mutableStateOf("") }

            SearchBar(
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_airplane_ticket_24),
                        contentDescription = null // decorative element
                    )
                },
                placeholder = {
                    Text(text = "What's your flight number?")
                },
                query = text,
                onQueryChange = { query ->
                    text = query
                },
                onSearch = {
                    state.eventSink(
                        SearchScreen.Event.Search(text)
                    )
                },
                active = true, onActiveChange = {}) {

                LazyColumn {
                    items(state.searchResults) { flight ->
                        FlightCard(flight)
                    }
                }

            }
            TextField(
                value = text,
                singleLine = true,
                onValueChange = { newText -> text = newText },
                label = { Text("Enter text") },
                keyboardActions = KeyboardActions(onAny = { state.eventSink(
                    SearchScreen.Event.Search(text)
                ) })
            )
            Button(onClick = {
                state.eventSink(
                    SearchScreen.Event.Search(text)
                )
            }) {
                Text(text = "Search")
            }
        }

    }
}