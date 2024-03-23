package jt.flights.search

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui

public class SearchUiFactory : Ui.Factory {
	public override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
		return when (screen) {
			is SearchScreen -> SearchUi()
			else -> null
		}
	}
}