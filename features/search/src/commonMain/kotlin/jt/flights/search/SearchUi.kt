package jt.flights.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.ui.Ui
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import jt.flights.search.ui.FlightCard

internal class SearchUi : Ui<SearchScreen.UiState> {
	@Composable
	override fun Content(state: SearchScreen.UiState, modifier: Modifier) {
		SearchUi(state = state, modifier = modifier)
	}
}

@Composable
internal fun SearchUi(
	state: SearchScreen.UiState,
	modifier: Modifier,
) {
	when (state) {
		is SearchScreen.UiState.Loaded -> LoadedSearchScreen(state)
		SearchScreen.UiState.Loading -> CircularProgressIndicator()
	}
}

@Composable
private fun LoadedSearchScreen(
	state: SearchScreen.UiState.Loaded,
	modifier: Modifier = Modifier,
) {
	val hint by remember { mutableStateOf(mutableStateOf(state.hint)) }
	Scaffold(
		modifier = modifier,
		topBar = {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
					.zIndex(1f)
			) {
				OutlinedTextField(
					label = { Text(text = hint.value) },
					value = state.search.value,
					onValueChange = { query ->
						state.eventSink(
							SearchScreen.Event.SearchChange(SearchTerm(query))
						)
					},
					keyboardActions = KeyboardActions {
						state.eventSink(
							SearchScreen.Event.Search(state.search)
						)
					},
					singleLine = true,
					modifier = Modifier.fillMaxWidth()
						.onFocusChanged { focusState ->
							if (focusState.isFocused) {
								state.eventSink(SearchScreen.Event.OpenSearch)
							} else  {
								state.eventSink(SearchScreen.Event.CloseSearch)
							}
						},
					visualTransformation = {
						TransformedText(AnnotatedString(it.text.uppercase(), it.spanStyles, it.paragraphStyles), OffsetMapping.Identity)
					},
					leadingIcon = {
						Icon(
							imageVector = Icons.Default.Search,
							contentDescription = null
						)
					},
					trailingIcon = {
						if (state.searching) {
							Icon(
								imageVector = Icons.Default.Clear,
								contentDescription = null,

								modifier = Modifier.clickable {
									state.eventSink(SearchScreen.Event.SearchChange(SearchTerm.empty))
								}
							)
						}
					},
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Search,
					),
				)
			}
		}
	) {
		Column(
			modifier = modifier
				.semantics { isTraversalGroup = true }
				.zIndex(1f)
				.fillMaxSize(),
			verticalArrangement = Arrangement.SpaceEvenly,
		) {

			when (val content = state.content) {
				is SearchScreen.UiState.Loaded.Content.Error -> Text("Error!")
				is SearchScreen.UiState.Loaded.Content.Found -> FlightCard(content.flight)
				is SearchScreen.UiState.Loaded.Content.NoResults -> NoSearchResults(content)
				SearchScreen.UiState.Loaded.Content.Initial -> Box(Modifier)
			}
		}
	}
}


@Composable
private fun SearchTermsList(
	loaded: SearchScreen.UiState.Loaded,
) {
	LazyColumn(
		modifier = Modifier.fillMaxSize()
	) {
		items(loaded.pastSearchTerms.size) { index ->
			val searchTerm = loaded.pastSearchTerms[index]
			ListItem(headlineContent = {
				Text(text = searchTerm.value)
			},
				modifier = Modifier.clickable {
					loaded.eventSink(
						SearchScreen.Event.Search(searchTerm)
					)
				}
			)
		}
	}
}

@Composable
internal fun NoSearchResults(
	noResults: SearchScreen.UiState.Loaded.Content.NoResults,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier,
	) {
		Text(noResults.message)
	}
}

@Preview
@Composable
internal fun PreviewNoSearchResults() {
	NoSearchResults(
		noResults = SearchScreen.UiState.Loaded.Content.NoResults("No results for flight number \"UAL1\"")
	)
}

@Composable
internal fun SearchError(
	error: SearchScreen.UiState.Loaded.Content.Error,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier,
	) {
		Text("Error")
	}
}

@Preview
@Composable
internal fun PreviewError() {
	SearchError(
		error = SearchScreen.UiState.Loaded.Content.Error("Oh oh!")
	)
}
