package uk.co.jordanterry.flights

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitConfig
import com.slack.circuit.foundation.CircuitContent
import dagger.hilt.android.AndroidEntryPoint
import jt.flight.flight.FlightScreen
import javax.inject.Inject

@AndroidEntryPoint
class FlightActivity : AppCompatActivity() {
    @Inject
    lateinit var circuitConfig: CircuitConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CircuitCompositionLocals(circuitConfig) {
                CircuitContent(FlightScreen())
            }
        }
    }
}


