package jt.flights.search.usecases

import jt.flights.search.SearchRepository
import jt.flights.search.SearchTerm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal fun interface SearchResultsForFlightNumber {
	suspend operator fun invoke(searchTerm: SearchTerm): FlightResults
}

internal fun SearchResultsForFlightNumber(
	searchRepository: SearchRepository,
): SearchResultsForFlightNumber {
	return SearchResultsForFlightNumber invoke@{ searchTerm ->
		val flightResults = searchRepository.search(flightNumber = searchTerm)
		if (flightResults.isNullOrEmpty()) return@invoke FlightResults.NoResultsFound
		val activeFlight = flightResults.firstOrNull { flight -> flight.isActive }
		if (activeFlight == null) return@invoke FlightResults.NoActiveFlightsFound
		FlightResults.ActiveFlightFound(activeFlight)
	}
}