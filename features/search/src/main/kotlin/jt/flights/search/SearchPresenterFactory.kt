//package jt.flights.search
//
//import com.slack.circuit.runtime.CircuitContext
//import com.slack.circuit.runtime.Navigator
//import com.slack.circuit.runtime.presenter.Presenter
//import com.slack.circuit.runtime.screen.Screen
//import com.squareup.anvil.annotations.ContributesMultibinding
//import jt.flights.di.AppScope
//import jt.flights.search.data.SearchRepository
//import javax.inject.Inject
//
//@ContributesMultibinding(AppScope::class, Presenter.Factory::class)
//class SearchPresenterFactory @Inject constructor(
//    private val searchRepository: SearchRepository
//) : Presenter.Factory {
//    override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? {
//        return when (screen) {
//            is SearchScreen -> SearchPresenter(navigator, searchRepository)
//            else -> null
//        }
//    }
//}