package jt.flight.search

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.ui.Ui
import jt.flight.home.SearchScreen
import javax.inject.Inject

class SearchUi : Ui<SearchScreen.SearchState> {
    @Composable
    override fun Content(state: SearchScreen.SearchState, modifier: Modifier) {
        Box {
            Text(state.title)
        }
    }
}

class SearchUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when(screen) {
            is SearchScreen -> SearchUi()
            else -> null
        }
    }
}
