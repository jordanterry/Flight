package jt.flights.search

import jt.flights.model.Data
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

fun interface SearchDataSource {
	suspend fun search(flightNumber: String): Data<List<Flight>>
}