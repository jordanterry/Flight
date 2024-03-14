package jt.flights.search

import com.newrelic.agent.android.NewRelic
import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.flightaware.FlightAwareBaseUrl
import jt.flights.flightaware.FlightAwareOkHttp
import jt.flights.flightaware.FlightAwareSearchResult
import jt.flights.flightaware.FlightsResult
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
class FlightAwareSearchDataSource @Inject constructor(
    private val json: Json,
    @FlightAwareOkHttp private val okHttpClient: OkHttpClient,
    @FlightAwareBaseUrl private val httpUrl: HttpUrl,
) : SearchDataSource {
    override suspend fun search(flightNumber: String): Result<FlightAwareSearchResult> {
        val searchUrl = httpUrl
            .newBuilder()
            .addPathSegment("flights")
            .addPathSegment(flightNumber)
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
                            .decodeFromString<FlightsResult>(responseBody.string())
                            .flightAwareFlights
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

