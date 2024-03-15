package jt.flights.flightaware

import jt.flights.model.Flight

public sealed interface FlightAwareSearchResult {
	public data class Results(
		val flights: List<Flight>,
	) : FlightAwareSearchResult

	public data object NoResults : FlightAwareSearchResult
	public data class ResultFailure(
		val errorCode: Int,
	) : FlightAwareSearchResult
}
