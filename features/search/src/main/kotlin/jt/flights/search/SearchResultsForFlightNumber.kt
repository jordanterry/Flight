package jt.flights.search

import jt.flights.model.Flight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class SearchResultsForFlightNumber(
	private val searchRepository: SearchRepository,
) {
	public suspend fun search(flightNumber: String): FlightResults = withContext(Dispatchers.IO) {
		val flightResults = searchRepository.search(flightNumber = flightNumber)
		if (flightResults.isNullOrEmpty()) return@withContext FlightResults.NoResultsFound
		val activeFlight = flightResults.firstOrNull { flight -> flight.isActive }
		if (activeFlight == null) return@withContext FlightResults.NoActiveFlightsFound
		FlightResults.ActiveFlightFound(activeFlight)
	}

	public sealed interface FlightResults {
		public data object JustSearch : FlightResults
		public data object NoActiveFlightsFound : FlightResults
		public data object NoResultsFound : FlightResults

		public data class ActiveFlightFound(
			val flight: Flight,
		) : FlightResults
	}
}