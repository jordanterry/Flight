package jt.flight.airports

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedScoped::class)
interface AirportModule {
    @Binds
    @IntoSet
    fun bindsFlightPresenterFactory(flightPresenterFactory: FlightPresenterFactory): Presenter.Factory

    @Binds
    @IntoSet
    fun bindsFlightUiFactory(flightUiFactory: FlightUiFactory): Ui.Factory
}