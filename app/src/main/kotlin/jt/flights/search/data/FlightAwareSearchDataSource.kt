package jt.flights.search.data

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.model.Flight
import jt.flights.networking.await
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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
                                .decodeFromString<FlightAwareApi.FlightsResult>(responseBody.string())
                                .flights
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
            Result.failure(
                exception = ioException
            )
        } catch (serializationException: SerializationException) {
            Result.failure(
                exception = serializationException
            )
        }
    }

    sealed interface FlightAwareSearchResult {
        data class Results(
            val flights: List<Flight>,
        ): FlightAwareSearchResult
        data object NoResults : FlightAwareSearchResult
        data class ResultFailure(
            val errorCode: Int,
        ) : FlightAwareSearchResult
    }

    private fun FlightAwareApi.Flight.toFlight(): Flight {
        return if (departureDelay != null || arrivalDelay != null) {
            Flight.Delayed(Flight.Id(ident))
        } else if (cancelled) {
            Flight.Cancelled(Flight.Id(ident))
        } else if (diverted) {
            Flight.Diverted(Flight.Id(ident))
        } else {
            Flight.OnTime(Flight.Id(ident))
        }
    }
}

