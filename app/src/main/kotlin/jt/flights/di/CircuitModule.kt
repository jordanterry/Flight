package jt.flights.di

import com.slack.circuit.foundation.CircuitConfig
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class CircuitModule {
    @Provides
    fun providesCircuitConfig(
        uiFactories: Set<@JvmSuppressWildcards Ui.Factory>,
        presenterFactories: Set<@JvmSuppressWildcards Presenter.Factory>
    ): CircuitConfig {
        return CircuitConfig.Builder().apply {
            addUiFactories(uiFactories)
            addPresenterFactories(presenterFactories)
        }.build()
    }
}