package jt.flights.search.data

import jt.flights.search.data.flightaware.FlightAwareSearchResult

fun interface SearchDataSource {
    suspend fun search(flightNumber: String): Result<FlightAwareSearchResult>
}

