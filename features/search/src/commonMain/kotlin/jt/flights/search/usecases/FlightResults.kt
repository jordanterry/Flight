package jt.flights.search.usecases

import jt.flights.model.Flight

public sealed interface FlightResults {
	public data object NoResultsFound : FlightResults

	public data class ActiveFlightFound(
		val flight: Flight,
	) : FlightResults
}