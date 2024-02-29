package jt.flights.search.data

fun interface SearchDataSource {
    suspend fun search(flightNumber: String): Result<FlightAwareSearchDataSource.FlightAwareSearchResult>
}

