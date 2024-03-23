package jt.flights.search

import jt.flights.flightaware.toFlight
import jt.flights.model.Flight
import jt.flights.networking.Network
import jt.flights.networking.Resource
import jt.flights.networking.fetch


public fun interface SearchDataSource {
	public suspend fun search(flightNumber: SearchTerm): Result<List<Flight>>
}

public fun NetworkSearchDataSource(
	flightAwareBaseUrl: String,
	network: Network,
): SearchDataSource {
	return FlightAwareSearchDataSource(flightAwareBaseUrl, network)
}

private class FlightAwareSearchDataSource(
	private val flightAwareBaseUrl: String,
	private val network: Network,
) : SearchDataSource {
	override suspend fun search(flightNumber: SearchTerm): Result<List<Flight>> {
		val resource = Resource
			.flightAwareSearch(
				flightAwareBaseUrl,
				flightNumber
			)
		return network
			.fetch(resource)
			.map { it.flightAwareFlights }
			.map { it.map { flightAwareFlight -> flightAwareFlight.toFlight() } }
	}
}
