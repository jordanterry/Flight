package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.ui.Ui

public class SearchUi : Ui<SearchScreen.UiState> {
	@Composable
	override fun Content(state: SearchScreen.UiState, modifier: Modifier) {
		SearchUi(state = state, modifier = modifier)
	}
}

@Composable
public expect fun SearchUi(state: SearchScreen.UiState, modifier: Modifier = Modifier)