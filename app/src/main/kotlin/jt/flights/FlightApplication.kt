package jt.flights

import android.app.Application
import com.newrelic.agent.android.NewRelic
import jt.flights.di.AppComponent

internal class FlightApplication : Application() {

	private val appComponent: AppComponent by lazy { AppComponent.create(this) }

	fun appComponent() = appComponent
}