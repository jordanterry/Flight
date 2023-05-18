package jt.flight.airports

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class FlightPresenter @AssistedInject constructor(
) : Presenter<AirportScreen.CounterState> {
    @Composable
    override fun present(): AirportScreen.CounterState {
        return AirportScreen.CounterState("Hello")
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): FlightPresenter
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
            is AirportScreen -> flightPresenterFactory.create()
            else -> null
        }
    }
}