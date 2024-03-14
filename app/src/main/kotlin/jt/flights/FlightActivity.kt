package jt.flights

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
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
			val darkTheme = false
			AppTheme(
				useDarkTheme = darkTheme
			) {

				// Update the edge to edge configuration to match the theme
				// This is the same parameters as the default enableEdgeToEdge call, but we manually
				// resolve whether or not to show dark theme using uiState, since it can be different
				// than the configuration's dark theme value based on the user preference.
				DisposableEffect(true) {
					enableEdgeToEdge(
						statusBarStyle = SystemBarStyle.auto(
							android.graphics.Color.TRANSPARENT,
							android.graphics.Color.TRANSPARENT,
						) { darkTheme },
						navigationBarStyle = SystemBarStyle.auto(
							lightScrim,
							darkScrim,
						) { darkTheme },
					)
					onDispose {}
				}
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

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

