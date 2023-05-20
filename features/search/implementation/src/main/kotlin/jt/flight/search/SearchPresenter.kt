package jt.flight.search

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import jt.flight.home.SearchScreen
import javax.inject.Inject

class SearchPresenter @AssistedInject constructor(
) : Presenter<SearchScreen.SearchState> {
    @Composable
    override fun present(): SearchScreen.SearchState {
        return SearchScreen.SearchState("Hello")
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): SearchPresenter
    }
}

class SearchPresenterFactory @Inject constructor(
    private val searchPresenterFactory: SearchPresenter.Factory
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is SearchScreen -> searchPresenterFactory.create()
            else -> null
        }
    }
}