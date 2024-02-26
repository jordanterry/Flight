package jt.flights

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.squareup.anvil.annotations.ContributesMultibinding
import jt.flights.di.ActivityKey
import jt.flights.di.AppScope
import jt.flights.di.CircuitComponent
import jt.flights.di.CircuitScope
import jt.flights.search.SearchScreen
import jt.flights.search.data.FlightAwareSearchDataSource
import javax.inject.Inject

@ContributesMultibinding(AppScope::class, boundType = Activity::class)
@ActivityKey(FlightActivity::class)
class FlightActivity @Inject constructor(
    private val circuit: Circuit,
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CircuitCompositionLocals(circuit) {
                val backstack = rememberSaveableBackStack { push(SearchScreen) }
                BackHandler(onBack = { /* do something on root */ })
                val navigator = rememberCircuitNavigator(backstack)
                NavigableCircuitContent(navigator, backstack)
            }
        }
    }
}


