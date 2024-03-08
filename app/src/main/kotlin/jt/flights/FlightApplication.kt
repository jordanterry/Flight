package jt.flights

import android.app.Application
import com.newrelic.agent.android.NewRelic
import jt.flights.di.AppComponent

class FlightApplication : Application() {

    private val appComponent: AppComponent by lazy { AppComponent.create(this) }

    fun appComponent() = appComponent

    override fun onCreate() {
        super.onCreate()
        NewRelic
            .withApplicationToken("")
            .start(this)
    }
}