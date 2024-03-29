package jt.flights

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import jt.flights.flightaware.FlightAwareApiInterceptor
import jt.flights.networking.OkHttpNetwork
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

internal class FlightApplication : Application() {

	lateinit var flights: Flights
		private set

	override fun onCreate() {
		super.onCreate()
		val cache = Cache(
			directory = File(cacheDir, "http_cache"),
			maxSize = 50L * 1024L * 1024L // 50 MiB
		)
		val okHttpClient = OkHttpClient
			.Builder()
			.addInterceptor(FlightAwareApiInterceptor.create(BuildConfig.FLIGHT_AWARE_TOKEN))
			.cache(cache)
			.build()
		val network = OkHttpNetwork(okHttpClient)
		val flightAwareUrl = "https://aeroapi.flightaware.com/aeroapi"
		val driver: SqlDriver = AndroidSqliteDriver(Searches.Schema, this, "searches.db")
		flights = Flights(flightAwareUrl, network, driver)
	}

}