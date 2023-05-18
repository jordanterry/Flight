package jt.flight.flight

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class FlightPresenter @AssistedInject constructor(
    @Assisted private val screen: FlightScreen,
) : Presenter<FlightScreen.CounterState> {
    @Composable
    override fun present(): FlightScreen.CounterState {
        return FlightScreen.CounterState("Hello")
    }

    @AssistedFactory
    fun interface Factory {
        fun create(screen: FlightScreen): FlightPresenter
    }
}

class FlightPresenterFactory @Inject constructor(
    private val flightPresenterFactory: FlightPresenter.Factory
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is FlightScreen -> flightPresenterFactory.create(screen)
            else -> null
        }
    }
}