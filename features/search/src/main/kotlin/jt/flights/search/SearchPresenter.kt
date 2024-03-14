package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.squareup.anvil.annotations.ContributesMultibinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import jt.flights.di.AppScope
import jt.flights.model.Flight
import jt.flights.search.data.ArrivalsRepository
import jt.flights.search.data.SearchRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


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
                .map {  flights ->
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
        val arrivalResult = runBlocking {
            when (val result = arrivalsRepository.search("LHR")) {
                is ArrivalsRepository.ArrivalResult.Found -> result.results
                ArrivalsRepository.ArrivalResult.NotFound -> emptyList()
            }
        }
        return SearchScreen.UiState(
            searchResults = flights,
            latestArrivals = arrivalResult,
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
