package jt.flights.search

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.flightaware.FlightAwareBaseUrl
import jt.flights.flightaware.FlightAwareFlight
import jt.flights.flightaware.FlightAwareOkHttp
import jt.flights.flightaware.FlightsResult
import jt.flights.flightaware.toFlight
import jt.flights.model.Data
import jt.flights.model.Flight
import jt.flights.networking.makeRequestAndUseBody
import jt.flights.operator.OperatorRepository
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@ContributesBinding(AppScope::class)
public class FlightAwareSearchDataSource @Inject constructor(
	private val json: Json,
	@FlightAwareOkHttp private val okHttpClient: OkHttpClient,
	@FlightAwareBaseUrl private val httpUrl: HttpUrl,
	private val operatorRepository: OperatorRepository,
) : SearchDataSource {
	override suspend fun search(flightNumber: String): Data<List<Flight>> {
		val search = okHttpClient.newCall(urlFromFlightNumber(flightNumber))
		return search.makeRequestAndUseBody { responseBody ->
			json
				.decodeFromString<FlightsResult>(responseBody.string())
				.flightAwareFlights
				.map { flightAwareFlight -> hydrateFlightAwareFlight(flightAwareFlight) }
		}
	}

	private suspend fun hydrateFlightAwareFlight(flightAwareFlight: FlightAwareFlight): Flight {
		val operatorIcao = flightAwareFlight.operatorIcao
		val operator = operatorRepository.details(operatorIcao)
		return flightAwareFlight
			.toFlight()
			.copy(
				operator = operator,
			)

	}

	private fun urlFromFlightNumber(flightNumber: String): Request {
		val searchUrl = httpUrl
			.newBuilder()
			.addPathSegment("flights")
			.addPathSegment(flightNumber)
			.build()
		return Request.Builder()
			.url(searchUrl)
			.get()
			.build()
	}
}
