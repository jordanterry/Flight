package jt.flights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import jt.flights.networking.OkHttpNetwork
import jt.flights.flightaware.FlightAwareApiInterceptor
import jt.flights.search.FlightAwareSearchDataSource
import jt.flights.search.Search
import jt.flights.search.SearchPresenter
import jt.flights.search.SearchRepositoryImpl
import jt.flights.search.SearchResultsForFlightNumber
import jt.flights.search.SearchScreen
import okhttp3.OkHttpClient

public class FlightActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()

		val okHttpClient = OkHttpClient
			.Builder()
			.addInterceptor(FlightAwareApiInterceptor.create())
			.build()
		val network = OkHttpNetwork(okHttpClient)
		val flightAwareUrl = "https://aeroapi.flightaware.com/aeroapi"
		val flightAwareSearchDataSource = FlightAwareSearchDataSource(flightAwareUrl, network)
		val searchRepository = SearchRepositoryImpl(flightAwareSearchDataSource)
		val searchResultsForFlightNumber = SearchResultsForFlightNumber(searchRepository)

		val circuit = Circuit.Builder()
			.addPresenter<SearchScreen, SearchScreen.UiState>(SearchPresenter(searchResultsForFlightNumber))
			.addUi<SearchScreen, SearchScreen.UiState> { uiState, modifier -> Search(uiState, modifier) }
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
