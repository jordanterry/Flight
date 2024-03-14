package jt.flights.search.data

import jt.flights.search.data.flightaware.FlightAwareSearchResult

fun interface AirportArrivalsDataSource {
    suspend fun search(icao: String): Result<FlightAwareSearchResult>
}

