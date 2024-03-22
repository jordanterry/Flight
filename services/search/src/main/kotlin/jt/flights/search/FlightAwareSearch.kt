package jt.flights.search

import jt.flights.flightaware.FlightsResult
import jt.flights.networking.Resource
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import okhttp3.Response

private val json = Json {
	ignoreUnknownKeys = true
}

internal fun Resource.Companion.flightAwareSearch(
	// Todo: How do we get this in here nicely?
	flightAwareBaseUrl: String,
	flightNumber: String
): Resource<FlightsResult> {
	return Resource(
		request = {
			val searchUrl = flightAwareBaseUrl.toHttpUrl()
				.newBuilder()
				.addPathSegment("flights")
				.addPathSegment(flightNumber)
				.build()
			Request.Builder()
				.url(searchUrl)
				.get()
				.build()
		},
		success = { response: Response ->
			val body = response.body
			requireNotNull(body) { "Response body cannot be empty." }
			if (response.isSuccessful) {
				val flights = json
					.decodeFromString<FlightsResult>(body.string())
				Result.success(flights)
			} else {
				Result.failure(RuntimeException("oh oh"))
			}
		}
	)
}