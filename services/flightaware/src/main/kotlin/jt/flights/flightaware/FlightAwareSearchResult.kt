package jt.flights.flightaware

import jt.flights.model.Flight

sealed interface FlightAwareSearchResult {
	data class Results(
		val flights: List<Flight>,
	) : FlightAwareSearchResult

	data object NoResults : FlightAwareSearchResult
	data class ResultFailure(
		val errorCode: Int,
	) : FlightAwareSearchResult
}
