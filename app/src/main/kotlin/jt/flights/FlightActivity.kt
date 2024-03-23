package jt.flights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import jt.flights.networking.OkHttpNetwork
import jt.flights.flightaware.FlightAwareApiInterceptor
import jt.flights.search.SearchPresenterFactory
import jt.flights.search.SearchScreen
import jt.flights.search.SearchUiFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

public class FlightActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		val cache = Cache(
			directory = File(application.cacheDir, "http_cache"),
			maxSize = 50L * 1024L * 1024L // 50 MiB
		)
		val okHttpClient = OkHttpClient
			.Builder()
			.addInterceptor(FlightAwareApiInterceptor.create())
			.cache(cache)
			.build()
		val network = OkHttpNetwork(okHttpClient)
		val flightAwareUrl = "https://aeroapi.flightaware.com/aeroapi"
		val driver: SqlDriver = AndroidSqliteDriver(Searches.Schema, this, "searches.db")

		val circuit = Circuit.Builder()
			.addPresenterFactory(SearchPresenterFactory(flightAwareUrl, network, driver))
			.addUiFactory(SearchUiFactory())
			.build()

		setContent {
			MaterialTheme {
				CircuitCompositionLocals(circuit) {
					val backstack = rememberSaveableBackStack(SearchScreen)
					BackHandler(onBack = { /* do something on root */ })
					val navigator = rememberCircuitNavigator(backstack)
					NavigableCircuitContent(navigator, backstack)
				}
			}
		}
	}
}
