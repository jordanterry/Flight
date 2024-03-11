package jt.flights.search.data

import jt.flights.search.data.flightaware.FlightAwareSearchDataSource

fun interface SearchDataSource {
    suspend fun search(flightNumber: String): Result<FlightAwareSearchDataSource.FlightAwareSearchResult>
}

