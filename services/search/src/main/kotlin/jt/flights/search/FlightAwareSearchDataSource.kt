package jt.flights.search

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.flightaware.FlightAwareBaseUrl
import jt.flights.flightaware.FlightAwareFlight
import jt.flights.flightaware.FlightsResult
import jt.flights.flightaware.toFlight
import jt.flights.model.Flight
import jt.flights.networking.Network
import jt.flights.networking.Resource
import jt.flights.networking.fetch
import jt.flights.operator.OperatorRepository
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import java.lang.RuntimeException
import javax.inject.Inject

@ContributesBinding(AppScope::class)
public class FlightAwareSearchDataSource @Inject constructor(
	@FlightAwareBaseUrl private val flightAwareBaseUrl: String,
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
