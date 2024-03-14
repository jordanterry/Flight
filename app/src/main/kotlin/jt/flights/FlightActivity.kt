package jt.flights

import android.app.Activity
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
import com.squareup.anvil.annotations.ContributesMultibinding
import jt.flights.di.ActivityKey
import jt.flights.di.AppScope
import jt.flights.search.SearchScreen
import javax.inject.Inject

@ContributesMultibinding(AppScope::class, boundType = Activity::class)
@ActivityKey(FlightActivity::class)
class FlightActivity @Inject constructor(
	private val circuit: Circuit,
) : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
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
