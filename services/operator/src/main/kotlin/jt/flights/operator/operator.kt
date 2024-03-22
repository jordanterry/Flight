package jt.flights.operator

import jt.flights.flightaware.FlightAwareOperator
import jt.flights.model.Operator
import jt.flights.networking.Resource
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request

internal fun Resource.Companion.operator(
	flightAwareBaseUrl: String,
	icao: String
): Resource<Operator> {
	return Resource(
		request = {
			val searchUrl = flightAwareBaseUrl
				.toHttpUrl()
				.newBuilder()
				.addPathSegment("operators")
				.addPathSegment(icao)
				.build()
			 Request.Builder()
				.url(searchUrl)
				.get()
				.build()
		},
		success = { response ->
			val body = response.body
			requireNotNull(body) { "Response body cannot be null." }
			val operator = Json {
				ignoreUnknownKeys = true
			}.decodeFromString<FlightAwareOperator>(body.string()).toOperatorOrNull()
			operator?.let { Result.success(it) } ?: Result.failure(RuntimeException("oh oh"))
		}
	)
}