package jt.flights.search.data

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import javax.inject.Inject

/**
 * Flight aware implementation of the [SearchRepository].
 */
@ContributesBinding(AppScope::class)
class FlightAwareSearchRepository @Inject constructor(
    private val searchDataSource: SearchDataSource,
) : SearchRepository {
    override suspend fun search(flightNumber: String): SearchRepository.SearchResults {
        val searchResult = searchDataSource.search(flightNumber = flightNumber)
        return if (searchResult.isSuccess) {
            when (val result = searchResult.getOrThrow()) {
                FlightAwareSearchDataSource.FlightAwareSearchResult.NoResults -> SearchRepository.SearchResults.NotFound
                is FlightAwareSearchDataSource.FlightAwareSearchResult.ResultFailure -> SearchRepository.SearchResults.NotFound
                is FlightAwareSearchDataSource.FlightAwareSearchResult.Results -> SearchRepository.SearchResults.Found(result.flights)
            }
        } else {
            SearchRepository.SearchResults.NotFound
        }
    }
}
