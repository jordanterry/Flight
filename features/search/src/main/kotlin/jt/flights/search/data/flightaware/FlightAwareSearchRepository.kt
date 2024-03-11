package jt.flights.search.data.flightaware

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.search.data.SearchDataSource
import jt.flights.search.data.SearchRepository
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
class FlightAwareSearchRepository @Inject constructor(
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

    private suspend fun flightNumberToSearchResults(flightNumber: String): Flow<SearchRepository.SearchResults> = flow {
        val searchResult = searchDataSource.search(
            flightNumber = flightNumber
        )
        val result = if (searchResult.isSuccess) {
            when (val result = searchResult.getOrThrow()) {
                FlightAwareSearchDataSource.FlightAwareSearchResult.NoResults -> SearchRepository.SearchResults.NotFound
                is FlightAwareSearchDataSource.FlightAwareSearchResult.ResultFailure -> SearchRepository.SearchResults.NotFound
                is FlightAwareSearchDataSource.FlightAwareSearchResult.Results -> SearchRepository.SearchResults.Found(
                    result.flights.activeOnly()
                )
            }
        } else {
            SearchRepository.SearchResults.NotFound
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
