package jt.flight.home

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class HomePresenter @AssistedInject constructor(
) : Presenter<HomeScreen.State> {
    @Composable
    override fun present(): HomeScreen.State {
        return HomeScreen.State(
            title = "Is your flight delayed?",
            initialFlightNumber = ""
        ) { event ->
            when (event) {
                is HomeScreen.Events.FlightNumberUpdated -> {
                    // Do something with this.
                }
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): HomePresenter
    }
}

class HomePresenterFactory @Inject constructor(
    private val homePresenterFactory: HomePresenter.Factory
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is HomeScreen -> homePresenterFactory.create()
            else -> null
        }
    }
}