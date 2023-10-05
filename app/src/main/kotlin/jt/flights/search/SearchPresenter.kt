package jt.flights.search

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.squareup.anvil.annotations.ContributesMultibinding
import jt.flights.di.AppScope
import javax.inject.Inject

class SearchPresenter(
    private val navigator: Navigator,
) : Presenter<SearchScreen.UiState> {
    @Composable
    override fun present(): SearchScreen.UiState {
        return SearchScreen.UiState { event ->
            when (event) {
                is SearchScreen.Event.Search -> TODO()
            }
        }
    }
}

@ContributesMultibinding(AppScope::class, Presenter.Factory::class)
class SearchPresenterFactory @Inject constructor() : Presenter.Factory {
    override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? {
        return when (screen) {
            is SearchScreen -> SearchPresenter(navigator)
            else -> null
        }
    }
}