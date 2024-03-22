package jt.flights.search

import jt.flights.flightaware.FlightAwareFlight
import jt.flights.flightaware.toFlight
import jt.flights.model.Flight
import jt.flights.networking.Network
import jt.flights.networking.Resource
import jt.flights.networking.fetch

public class FlightAwareSearchDataSource(
	private val flightAwareBaseUrl: String,
	private val network: Network,
) : SearchDataSource {
	public override suspend fun search(flightNumber: String): Result<List<Flight>> {
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

	private suspend fun hydrateFlightAwareFlight(flightAwareFlight: FlightAwareFlight): Flight {
//		val operatorIcao = flightAwareFlight.operatorIcao
//		val operator = operatorRepository.details(operatorIcao)
		return flightAwareFlight
			.toFlight()
//			.copy(
//				operator = operator,
//			)

	}
}
