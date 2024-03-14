//package jt.flights.search
//
//import com.slack.circuit.runtime.CircuitContext
//import com.slack.circuit.runtime.screen.Screen
//import com.slack.circuit.runtime.ui.Ui
//import com.squareup.anvil.annotations.ContributesMultibinding
//import jt.flights.di.AppScope
//import javax.inject.Inject
//
//@ContributesMultibinding(AppScope::class, Ui.Factory::class)
//class SearchUiFactory @Inject constructor() : Ui.Factory {
//    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
//        return when (screen) {
//            is SearchScreen -> SearchUi()
//            else -> null
//        }
//    }
//}