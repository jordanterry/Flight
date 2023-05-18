package jt.flight.flight

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityComponent::class)
interface FlightModule {
    @Binds
    @IntoSet
    fun bindsFlightPresenterFactory(flightPresenterFactory: FlightPresenterFactory): Presenter.Factory

    @Binds
    @IntoSet
    fun bindsFlightUiFactory(flightUiFactory: FlightUiFactory): Ui.Factory
}