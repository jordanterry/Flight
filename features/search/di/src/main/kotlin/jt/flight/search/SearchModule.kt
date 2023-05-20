package jt.flight.search

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SearchModule {
    @Binds
    @IntoSet
    fun bindSearchPresenterFactory(searchPresenterFactory: SearchPresenterFactory): Presenter.Factory

    @Binds
    @IntoSet
    fun bindsSearchUiFactory(searchUiFactory: SearchUiFactory): Ui.Factory
}