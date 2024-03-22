package jt.flights.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import jt.flights.search.ui.DetailedFlightCard
import jt.flights.search.ui.FlightHeader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun Search(
	state: SearchScreen.UiState,
	modifier: Modifier = Modifier
) {
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
					when (state.presentation) {
						SearchPresenter.FlightPresentation.Loading -> CircularProgressIndicator(
							modifier = Modifier.padding(2.dp)
						)
						is SearchPresenter.FlightPresentation.Loaded -> {
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

			when (val presentation = state.presentation) {
				is SearchPresenter.FlightPresentation.Loaded ->  {
					when (presentation.flightResults) {
						is SearchResultsForFlightNumber.FlightResults.ActiveFlightFound -> DetailedFlightCard(presentation.flightResults.flight)
						SearchResultsForFlightNumber.FlightResults.NoActiveFlightsFound -> Text("There are no active flights.")
						SearchResultsForFlightNumber.FlightResults.NoResultsFound -> Text("No results found.")
						SearchResultsForFlightNumber.FlightResults.JustSearch -> Box(modifier)
					}
				}
				SearchPresenter.FlightPresentation.Loading -> CircularProgressIndicator()
			}
		}
	}
}
