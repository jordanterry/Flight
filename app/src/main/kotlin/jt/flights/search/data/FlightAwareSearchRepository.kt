package jt.flights.search.data

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Flight aware implementation of the [SearchRepository].
 */
@ContributesBinding(AppScope::class)
class FlightAwareSearchRepository @Inject constructor(
    private val searchDataSource: SearchDataSource,
) : SearchRepository {

    private val _state = MutableStateFlow<String?>(null)

    override val results = _state
        .map { flightNumber ->
            if (flightNumber != null) {
                val searchResult = searchDataSource.search(flightNumber = flightNumber)
                if (searchResult.isSuccess) {
                    when (val result = searchResult.getOrThrow()) {
                        FlightAwareSearchDataSource.FlightAwareSearchResult.NoResults -> SearchRepository.SearchResults.NotFound
                        is FlightAwareSearchDataSource.FlightAwareSearchResult.ResultFailure -> SearchRepository.SearchResults.NotFound
                        is FlightAwareSearchDataSource.FlightAwareSearchResult.Results -> SearchRepository.SearchResults.Found(
                            result.flights.filter { it.isActive }
                        )
                    }
                } else {
                    SearchRepository.SearchResults.NotFound
                }
            } else {
                SearchRepository.SearchResults.NotFound
            }
        }
    override suspend fun search(flightNumber: String) {
        _state.emit(flightNumber)
    }
}
