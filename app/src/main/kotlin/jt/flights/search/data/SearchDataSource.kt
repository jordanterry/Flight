package jt.flights.search.data

import jt.flights.model.Flight

fun interface SearchDataSource {
    suspend fun search(flightNumber: String): Result<List<Flight>>
}

