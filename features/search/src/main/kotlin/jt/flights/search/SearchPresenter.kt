package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import jt.flights.arrivals.ArrivalsRepository
import jt.flights.di.AppScope
import jt.flights.model.Flight

class SearchPresenter @AssistedInject constructor(
	private val searchRepository: SearchRepository,
) : Presenter<SearchScreen.UiState> {
	@Composable
	override fun present(): SearchScreen.UiState {
		var flightNumber by rememberRetained { mutableStateOf("") }
		var results by rememberRetained { mutableStateOf(emptyList<Flight>()) }
		LaunchedEffect(flightNumber) {
			results = searchRepository.search(flightNumber = flightNumber) ?: emptyList()
		}

		return SearchScreen.UiState(
			searchResults = results,
		) { event ->
			when (event) {
				is SearchScreen.Event.Search -> {
					flightNumber = event.query
				}
			}
		}
	}

	@CircuitInject(SearchScreen::class, AppScope::class)
	@AssistedFactory
	fun interface Factory {
		fun create(): SearchPresenter
	}
}
