package jt.flights.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.squareup.anvil.annotations.ContributesMultibinding
import jt.flights.di.AppScope
import javax.inject.Inject

class SearchUi : Ui<SearchScreen.UiState> {
    @Composable
    override fun Content(state: SearchScreen.UiState, modifier: Modifier) {
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
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Search")
        }
    }
}