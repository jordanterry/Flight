package jt.flights.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.slack.circuit.codegen.annotations.CircuitInject
import jt.flights.di.AppScope
import jt.flights.search.ui.DetailedFlightCard
import jt.flights.search.ui.FlightHeader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@CircuitInject(SearchScreen::class, AppScope::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun Search(
	state: SearchScreen.UiState,
	modifier: Modifier = Modifier
) {
	println("New State: $state")

	var text by remember { mutableStateOf("") }
	Scaffold(
		modifier = modifier
			.safeDrawingPadding(),
	) {
		Column(
			modifier = modifier
				.semantics { isTraversalGroup = true }
				.zIndex(1f)
				.fillMaxSize(),
			verticalArrangement = Arrangement.SpaceEvenly,
		) {
			val active = remember { mutableStateOf(false) }
			SearchBar(
				shadowElevation = 4.dp,
				modifier = Modifier
					.align(Alignment.CenterHorizontally),
				trailingIcon = {
					if (state.loading) {
						CircularProgressIndicator(
							modifier = Modifier.padding(2.dp)
						)
					} else {
						if (active.value) {
							Icon(
								imageVector = Icons.Default.Close,
								contentDescription = null,
								modifier = Modifier.clickable {
									active.value = false
								}
							)
						} else {
							Icon(
								imageVector = Icons.Default.Search,
								contentDescription = null
							)
						}
					}
				},
				placeholder = {
					Text(text = "What's your flight number?")
				},
				query = text,
				onQueryChange = { query ->
					text = query
				},
				active = active.value,
				onSearch = {
					state.eventSink(
						SearchScreen.Event.Search(text)
					)
					active.value = false
				},
				onActiveChange = { active.value = it }
			) { }
			println("Flights: " + state.searchResults.size)
			if (state.searchResults.isNotEmpty()) {
				println("Flights rendered: " + state.searchResults.size)
				LazyColumn(
					modifier = Modifier
						.padding(18.dp)
						.fillMaxHeight(),
				) {
					items(state.searchResults) { presentation ->
						when (presentation) {
							is SearchPresenter.FlightPresentation.Header -> Text(presentation.title)
							is SearchPresenter.FlightPresentation.ActiveFlight -> DetailedFlightCard(presentation.flight)
							is SearchPresenter.FlightPresentation.NormalFlight -> FlightHeader(presentation.flight)
						}
					}
				}
			}
		}
	}
}
