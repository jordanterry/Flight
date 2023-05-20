package jt.flight.home

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
interface HomeModule {
    @Binds
    @IntoSet
    fun bindsHomePresenterFactory(homePresenterFactory: HomePresenterFactory): Presenter.Factory

    @Binds
    @IntoSet
    fun bindsHomeUiFactory(homeUiFactory: HomeUiFactory): Ui.Factory
}