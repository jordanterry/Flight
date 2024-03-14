package jt.flights.search

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.model.Data
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import javax.inject.Inject

/**
 * Flight aware implementation of the [SearchRepository].
 */
@ContributesBinding(AppScope::class)
class SearchRepositoryImpl @Inject constructor(
	private val searchDataSource: SearchDataSource,
) : SearchRepository {

	private val _search = MutableStateFlow<String?>(null)

	@OptIn(ExperimentalCoroutinesApi::class)
	override val results = _search
		.flatMapConcat { flightNumber ->
			if (flightNumber != null) {
				flightNumberToSearchResults(flightNumber = flightNumber)
			} else {
				flowOf(SearchRepository.SearchResults.NotFound)
			}
		}

	private suspend fun flightNumberToSearchResults(flightNumber: String): Flow<SearchRepository.SearchResults> =
		flow {
			val result = when (val searchResult = searchDataSource.search(flightNumber)) {
				is Data.Error -> SearchRepository.SearchResults.NotFound
				is Data.None -> SearchRepository.SearchResults.NotFound
				is Data.Some -> SearchRepository.SearchResults.Found(searchResult.data.activeOnly())
			}
			if (currentCoroutineContext().isActive) {
				emit(result)
			}
		}

	override suspend fun search(flightNumber: String) {
		_search.emit(flightNumber)
	}

	private fun List<jt.flights.model.Flight>.activeOnly(): List<jt.flights.model.Flight> {
		return filter { flight -> flight.isActive }
	}
}
