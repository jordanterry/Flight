package jt.flights

import android.app.Application
import jt.flights.di.AppComponent
//import jt.flights.di.DaggerAppComponent

class FlightApplication : Application() {

    private val appComponent: AppComponent by lazy { AppComponent.create(this) }

    fun appComponent() = appComponent
}