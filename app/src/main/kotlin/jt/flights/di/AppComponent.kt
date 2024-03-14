package jt.flights.di

import android.app.Activity
import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Provider

@SingleIn(AppScope::class)
@MergeComponent(
	AppScope::class,
	modules = [
		ActivityModule::class,
	]
)
interface AppComponent {
	val activityProviders: Map<Class<out Activity>, Provider<Activity>>

	@Component.Factory
	interface Factory {
		fun create(@ApplicationContext @BindsInstance context: Context): AppComponent
	}

	companion object {
		fun create(context: Context): AppComponent = DaggerAppComponent.factory().create(context)
	}
}
