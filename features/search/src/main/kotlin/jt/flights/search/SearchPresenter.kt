package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import jt.flights.arrivals.ArrivalsRepository
import jt.flights.di.AppScope
import jt.flights.model.Flight
import kotlinx.coroutines.flow.map

class SearchPresenter @AssistedInject constructor(
	@Assisted private val navigator: Navigator,
	private val searchRepository: SearchRepository,
	private val arrivalsRepository: ArrivalsRepository,
) : Presenter<SearchScreen.UiState> {
	@Composable
	override fun present(): SearchScreen.UiState {
		var flightNumber by rememberSaveable { mutableStateOf<String?>(null) }
		if (flightNumber != null) {
			LaunchedEffect(Unit) {
				searchRepository.search(flightNumber = flightNumber!!)
				flightNumber = null
			}
		}

		val flightResults by produceState<List<Flight>>(initialValue = emptyList()) {
			searchRepository
				.results
				.map { flights ->
					when (flights) {
						is SearchRepository.SearchResults.Found -> flights.results.filter { it.isActive }
						SearchRepository.SearchResults.NotFound -> emptyList()
					}
				}
				.collect {
					value = it
				}
		}
		val flights = flightResults
		return SearchScreen.UiState(
			searchResults = flights,
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
		fun create(navigator: Navigator): SearchPresenter
	}
}
