package jt.flights.di

import android.app.Activity
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Provides
import jt.flights.networking.NetworkingScope
import javax.inject.Provider

@ContributesSubcomponent(
    scope = CircuitScope::class,
    parentScope = AppScope::class,
    modules = [
        ActivityModule::class,
    ]
)
interface CircuitComponent {
    val activityProviders: Map<Class<out Activity>, Provider<Activity>>
    @ContributesSubcomponent.Factory
    public interface Factory {
        public fun create(): CircuitComponent
    }
    @ContributesTo(AppScope::class)
    interface ParentComponent {
        fun createCircuitComponentFactory(): CircuitComponent.Factory
    }
}