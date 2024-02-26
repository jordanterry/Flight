package jt.flights.search.data

import jt.flights.model.Flight

interface SearchRepository {
    suspend fun search(flightNumber: String): SearchResults

    sealed interface SearchResults {
        data class Found(
            val results: List<Flight>
        ) : SearchResults

        data object NotFound : SearchResults
    }
}
