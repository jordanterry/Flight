package jt.flights.search

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.model.Data
import jt.flights.model.Flight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Flight aware implementation of the [SearchRepository].
 */
@ContributesBinding(AppScope::class)
public class SearchRepositoryImpl @Inject constructor(
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
