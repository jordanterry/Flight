package jt.flights.search.data

import jt.flights.model.Flight
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.io.Serial
import java.lang.RuntimeException
import javax.inject.Inject

class FlightAwareSearchDataSource @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val json: Json,
    @FlightAwareBaseUrl private val httpUrl: HttpUrl,
) : SearchDataSource {
    override suspend fun search(flightNumber: String): Result<List<Flight>> {
        val request = Request
            .Builder()
            .addHeader("x-apikey", "")
            .url("https://aeroapi.flightaware.com/aeroapi/flights/$flightNumber")
            .get()
            .build()

        val search = okHttpClient.newCall(request)
        return try {
            val searchResult = search.await()
            val body = searchResult.body?.toString()
            if (searchResult.isSuccessful && body != null) {
                val flightsResult = json.decodeFromString<FlightAware.FlightsResult>(body)
                Result.success(flightsResult.flights.map { Flight(Flight.Id(it.ident)) })
            } else {
                Result.failure(RuntimeException())
            }
        } catch (ioException: IOException) {
            Result.failure(ioException)
        }

    }
}

internal suspend inline fun Call.await(): Response {
    return suspendCancellableCoroutine { continuation ->
        val callback = ContinuationCallback(this, continuation)
        enqueue(callback)
        continuation.invokeOnCancellation(callback)
    }
}
interface FlightAware {
    @Serializable
    data class Flight(
        val ident: String,
        val origin: Airport,
        val destination: Airport,
        @SerialName("aircraft_type")
        val aircraftType: String,
        @SerialName("scheduled_off")
        val scheduledOff: String,
        @SerialName("estimated_off")
        val estimatedOff: String,
        @SerialName("operator")
        val operator: String,
        @SerialName("operator_icao")
        val operatorIcao: String,
        @SerialName("blocked")
        val blocked: Boolean,
        @SerialName("diverted")
        val diverted: Boolean,
        @SerialName("cancelled")
        val cancelled: Boolean,
    )

    @Serializable
    data class Airport(
        val code: String,
        val name: String,
    )

    @Serializable
    data class FlightsResult(
        val flights: List<Flight>
    )
}
