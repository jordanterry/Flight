package jt.flights.search

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.squareup.anvil.annotations.ContributesMultibinding
import jt.flights.di.AppScope
import jt.flights.search.data.FlightAwareSearchDataSource
import jt.flights.search.data.FlightAwareSearchRepository
import jt.flights.search.data.SearchRepository
import javax.inject.Inject

class SearchPresenter(
    private val navigator: Navigator,
    private val searchRepository: SearchRepository,
) : Presenter<SearchScreen.UiState> {
    @Composable
    override fun present(): SearchScreen.UiState {
        LaunchedEffect("") {
            when (val flights = searchRepository.search("UAL1")) {
                is SearchRepository.SearchResults.Found -> flights.results.forEach(::println)
                SearchRepository.SearchResults.NotFound -> println("ERROR")
            }
        }
        return SearchScreen.UiState { event ->
            when (event) {
                is SearchScreen.Event.Search -> TODO()
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