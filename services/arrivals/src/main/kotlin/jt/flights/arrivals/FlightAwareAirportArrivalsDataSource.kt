package jt.flights.arrivals

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.flightaware.ArrivalsResult
import jt.flights.flightaware.FlightAwareBaseUrl
import jt.flights.flightaware.FlightAwareOkHttp
import jt.flights.flightaware.toFlight
import jt.flights.model.Data
import jt.flights.model.Flight
import jt.flights.networking.makeRequestAndUseBody
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class FlightAwareAirportArrivalsDataSource @Inject constructor(
	private val json: Json,
	@FlightAwareOkHttp private val okHttpClient: OkHttpClient,
	@FlightAwareBaseUrl private val httpUrl: HttpUrl,
) : AirportArrivalsDataSource {
	override suspend fun arrivals(icao: String): Data<List<Flight>> {
		val arrivals = okHttpClient.newCall(requestFromIcao(icao))
		return arrivals.makeRequestAndUseBody { responseBody ->
			json
				.decodeFromString<ArrivalsResult>(responseBody.string())
				.arrivals
				.map { flightAwareFlight -> flightAwareFlight.toFlight() }
		}
	}

	private fun requestFromIcao(icao: String): Request {
		val searchUrl = httpUrl
			.newBuilder()
			.addPathSegment("airports")
			.addPathSegment(icao)
			.addPathSegment("flights")
			.addPathSegment("arrivals")
			.build()
		return Request.Builder()
			.url(searchUrl)
			.get()
			.build()
	}
}

