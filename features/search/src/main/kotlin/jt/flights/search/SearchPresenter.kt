package jt.flights.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import jt.flights.arrivals.ArrivalsRepository
import jt.flights.di.AppScope
import jt.flights.model.Flight

public class SearchPresenter @AssistedInject constructor(
	private val searchRepository: SearchRepository,
) : Presenter<SearchScreen.UiState> {
	@Composable
	override fun present(): SearchScreen.UiState {
		var flightNumber by rememberRetained { mutableStateOf("") }
		var results by rememberRetained { mutableStateOf(emptyList<FlightPresentation>()) }
		LaunchedEffect(flightNumber) {
			val flightResults = searchRepository.search(flightNumber = flightNumber) ?: emptyList()
			if (flightResults.isNotEmpty()) {
				results = buildList {
					if (flightResults.first().isActive) {
						add(FlightPresentation.Header("Active"))
						add(FlightPresentation.ActiveFlight(flightResults.first()))
						if (flightResults.size > 1) {
							add(FlightPresentation.Header("Future"))
						}
					}
					addAll(flightResults.filter { !it.isActive }
						.map { FlightPresentation.NormalFlight(it) })

				}
			}
		}

		return SearchScreen.UiState(
			searchResults = results,
		) { event ->
			when (event) {
				is SearchScreen.Event.Search -> {
					flightNumber = event.query
				}
			}
		}
	}

	@CircuitInject(SearchScreen::class, AppScope::class)
	@AssistedFactory
	public fun interface Factory {
		public fun create(): SearchPresenter
	}

	public sealed interface FlightPresentation {
		@JvmInline
		public value class Header(public val title: String): FlightPresentation
		@JvmInline
		public value class ActiveFlight(public val flight: Flight): FlightPresentation
		@JvmInline
		public value class NormalFlight(public val flight: Flight): FlightPresentation
	}
}
