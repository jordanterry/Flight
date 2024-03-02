package jt.flights

import android.app.Application
import jt.flights.di.AppComponent

class FlightApplication : Application() {

    private val appComponent: AppComponent by lazy { AppComponent.create(this) }

    fun appComponent() = appComponent
}