package jt.flights.search.data.flightaware

import com.newrelic.agent.android.NewRelic
import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.networking.await
import jt.flights.search.data.AirportArrivalsDataSource
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

    private fun Flight.toFlight(): jt.flights.model.Flight {
        val flightInfo = if ((departureDelay != null && departureDelay > 10) || (arrivalDelay != null && arrivalDelay > 10)) {
            jt.flights.model.Flight.Info.Delayed
        } else if (cancelled) {
            jt.flights.model.Flight.Info.Cancelled
        } else if (diverted) {
            jt.flights.model.Flight.Info.Diverted
        } else {
            jt.flights.model.Flight.Info.OnTime
        }
        return jt.flights.model.Flight(
            id = jt.flights.model.Flight.Id(ident),
            from = jt.flights.model.Flight.Airport(
                name = origin.name,
                iataCode = origin.iataCode,
            ),
            fromInstant = scheduledOff,
            toInstant = scheduledIn,
            to = jt.flights.model.Flight.Airport(
                name = destination.name,
                iataCode = destination.iataCode,
            ),
            isActive = progressPercent in 1..99,
            flightInfo = flightInfo,
        )
    }
}

