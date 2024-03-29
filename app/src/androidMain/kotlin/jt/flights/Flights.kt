package jt.flights

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.sqldelight.db.SqlDriver
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import jt.flights.networking.Network
import jt.flights.search.SearchPresenterFactory
import jt.flights.search.SearchScreen
import jt.flights.search.SearchUiFactory

internal fun interface Flights {
	@Composable
	fun Render()
}

internal fun Flights(
	flightAwareUrl: String,
	network: Network,
	driver: SqlDriver,
): Flights {
	val circuit = Circuit.Builder()
		.addPresenterFactory(SearchPresenterFactory(flightAwareUrl, network, driver))
		.addUiFactory(SearchUiFactory())
		.build()
	return Flights {
		MaterialTheme {
			CircuitCompositionLocals(circuit) {
				val backstack = rememberSaveableBackStack(SearchScreen)
				val navigator = rememberCircuitNavigator(backstack)
				NavigableCircuitContent(navigator, backstack, modifier = Modifier.safeDrawingPadding())
			}
		}
	}
}
