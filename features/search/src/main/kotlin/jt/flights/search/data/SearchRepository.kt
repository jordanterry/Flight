package jt.flights.search.data

import jt.flights.model.Flight
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    val results: Flow<SearchResults>

    suspend fun search(flightNumber: String)

    sealed interface SearchResults {
        data class Found(
            val results: List<Flight>
        ) : SearchResults

        data object NotFound : SearchResults
    }
}
