package jt.flights.arrivals

import com.newrelic.agent.android.NewRelic
import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.flightaware.ArrivalsResult
import jt.flights.flightaware.FlightAwareBaseUrl
import jt.flights.flightaware.FlightAwareOkHttp
import jt.flights.flightaware.FlightAwareSearchResult
import jt.flights.flightaware.toFlight
import jt.flights.networking.await
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class FlightAwareAirportArrivalsDataSource @Inject constructor(
    private val json: Json,
    @FlightAwareOkHttp private val okHttpClient: OkHttpClient,
    @FlightAwareBaseUrl private val httpUrl: HttpUrl,
) : AirportArrivalsDataSource {
    override suspend fun search(icao: String): Result<FlightAwareSearchResult> {
        val searchUrl = httpUrl
            .newBuilder()
            .addPathSegment("airports")
            .addPathSegment(icao)
            .addPathSegment("flights")
            .addPathSegment("arrivals")
            .build()
        val request = Request.Builder()
            .url(searchUrl)
            .get()
            .build()

        val search = okHttpClient.newCall(request)
        return try {
            search.await().use { response ->
                if (response.isSuccessful) {
                    response.body?.use { responseBody ->
                        val flights = json
                            .decodeFromString<ArrivalsResult>(responseBody.string())
                            .arrivals
                            .map { flightAwareFlight -> flightAwareFlight.toFlight() }
                        Result.success(
                            value = FlightAwareSearchResult.Results(flights)
                        )
                    } ?: Result.success(
                        value = FlightAwareSearchResult.NoResults
                    )
                } else {
                    Result.success(
                        value = FlightAwareSearchResult.ResultFailure(
                            errorCode = response.code,
                        )
                    )
                }
            }
        } catch (ioException: IOException) {
            NewRelic.recordHandledException(ioException)
            ioException.printStackTrace()
            Result.failure(
                exception = ioException
            )
        } catch (serializationException: SerializationException) {
            NewRelic.recordHandledException(serializationException)
            serializationException.printStackTrace()
            Result.failure(
                exception = serializationException
            )
        }
    }
}

