package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.squareup.anvil.annotations.ContributesMultibinding
import jt.flights.di.AppScope
import jt.flights.search.data.SearchRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchPresenter(
    private val navigator: Navigator,
    private val searchRepository: SearchRepository,
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
        
        val flightResults by produceState<List<jt.flights.model.Flight>>(initialValue = emptyList()) {
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
        return SearchScreen.UiState(
            searchResults = flights
        ) { event ->
            when (event) {
                is SearchScreen.Event.Search -> {
                    flightNumber = event.query
                }
            }
        }
    }
}

@ContributesMultibinding(AppScope::class, Presenter.Factory::class)
class SearchPresenterFactory @Inject constructor(
    private val searchRepository: SearchRepository
) : Presenter.Factory {
    override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? {
        return when (screen) {
            is SearchScreen -> SearchPresenter(navigator, searchRepository)
            else -> null
        }
    }
}