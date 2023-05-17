package jt.flight.flight;

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
    @Assisted private val screen: jt.flight.flight.FlightScreen,
    @Assisted private val navigator: Navigator,
) : Presenter<jt.flight.flight.FlightScreen.CounterState> {
    @Composable
    override fun present(): jt.flight.flight.FlightScreen.CounterState {
        return jt.flight.flight.FlightScreen.CounterState("Hello")
    }

    @AssistedFactory
    fun interface Factory {
        fun create(screen: jt.flight.flight.FlightScreen, navigator: Navigator): FlightPresenter
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
            is jt.flight.flight.FlightScreen -> flightPresenterFactory.create(screen, navigator)
            else -> null
        }
    }
}