package jt.flights.search

import jt.flights.model.Flight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Flight aware implementation of the [SearchRepository].
 */
public class SearchRepositoryImpl(
	private val searchDataSource: SearchDataSource,
) : SearchRepository {
	override suspend fun search(flightNumber: SearchTerm): List<Flight>? {
		if (flightNumber.value.isEmpty()) return emptyList()
		val result = searchDataSource.search(flightNumber)
		return if (result.isSuccess) {
			result.getOrThrow()
		} else {
			null
		}
	}
}
