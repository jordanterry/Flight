package jt.flights.arrivals

import jt.flights.flightaware.FlightAwareSearchResult
import jt.flights.model.Flight

interface ArrivalsRepository {

    suspend fun search(flightNumber: String): ArrivalResult

    sealed interface ArrivalResult {
        data class Found(
            val results: List<Flight>
        ) : ArrivalResult

        data object NotFound : ArrivalResult
    }
}

fun interface AirportArrivalsDataSource {
    suspend fun search(icao: String): Result<FlightAwareSearchResult>
}