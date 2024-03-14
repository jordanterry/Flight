package jt.flights.search

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.model.Data
import jt.flights.model.Flight
import javax.inject.Inject

/**
 * Flight aware implementation of the [SearchRepository].
 */
@ContributesBinding(AppScope::class)
class SearchRepositoryImpl @Inject constructor(
	private val searchDataSource: SearchDataSource,
) : SearchRepository {
	override suspend fun search(flightNumber: String): List<Flight>? {
		if (flightNumber.isEmpty()) return emptyList()
		return when (val searchResult = searchDataSource.search(flightNumber)) {
			is Data.Error, is Data.None -> null
			is Data.Some -> searchResult.data
		}
	}
}
