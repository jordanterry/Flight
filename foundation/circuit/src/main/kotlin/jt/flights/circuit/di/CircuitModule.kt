package jt.flights.circuit.di

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import jt.flights.di.AppScope

@ContributesTo(
	scope = AppScope::class
)
@Module
public interface CircuitModule {
	@Multibinds
	public fun presenterFactories(): Set<Presenter.Factory>

	@Multibinds
	public fun viewFactories(): Set<Ui.Factory>

	public companion object {
		@Provides
		public fun provideCircuit(
			presenterFactories: @JvmSuppressWildcards Set<Presenter.Factory>,
			uiFactories: @JvmSuppressWildcards Set<Ui.Factory>,
		): Circuit {
			return Circuit.Builder()
				.addPresenterFactories(presenterFactories)
				.addUiFactories(uiFactories)
				.build()
		}
	}
}