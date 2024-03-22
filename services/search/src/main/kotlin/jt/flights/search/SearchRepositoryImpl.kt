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
	override suspend fun search(flightNumber: String): List<Flight>? = withContext(Dispatchers.IO) {
		if (flightNumber.isEmpty()) return@withContext emptyList()
		val result = searchDataSource.search(flightNumber)
		if (result.isSuccess) {
			result.getOrThrow()
		} else {
			null
		}
	}
}
