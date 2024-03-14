package jt.flights.search.data.flightaware

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.search.data.AirportArrivalsDataSource
import jt.flights.search.data.ArrivalsRepository
import jt.flights.search.data.SearchDataSource
import jt.flights.search.data.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Flight aware implementation of the [SearchRepository].
 */
@ContributesBinding(AppScope::class)
class FlightAwareArrivalsRepository @Inject constructor(
    private val airportArrivalsDataSource: AirportArrivalsDataSource,
) : ArrivalsRepository {

    private val _search = MutableStateFlow<String?>(null)

    private suspend fun flightNumberToSearchResults(flightNumber: String): Flow<SearchRepository.SearchResults> = flow {
        val searchResult = airportArrivalsDataSource.search(
            icao = flightNumber
        )
        val result = if (searchResult.isSuccess) {
            when (val result = searchResult.getOrThrow()) {
                FlightAwareSearchResult.NoResults -> SearchRepository.SearchResults.NotFound
                is FlightAwareSearchResult.ResultFailure -> SearchRepository.SearchResults.NotFound
                is FlightAwareSearchResult.Results -> SearchRepository.SearchResults.Found(
                    result.flights
                )
            }
        } else {
            SearchRepository.SearchResults.NotFound
        }
        if (currentCoroutineContext().isActive) {
            emit(result)
        }
    }

    override suspend fun search(flightNumber: String): ArrivalsRepository.ArrivalResult = withContext(Dispatchers.IO) {
        val searchResult = airportArrivalsDataSource.search(
            icao = flightNumber
        )
        if (searchResult.isSuccess) {
            when (val result = searchResult.getOrThrow()) {
                FlightAwareSearchResult.NoResults -> ArrivalsRepository.ArrivalResult.NotFound
                is FlightAwareSearchResult.ResultFailure -> ArrivalsRepository.ArrivalResult.NotFound
                is FlightAwareSearchResult.Results -> ArrivalsRepository.ArrivalResult.Found(
                    result.flights
                )
            }
        } else {
            ArrivalsRepository.ArrivalResult.NotFound
        }
    }

    private fun List<jt.flights.model.Flight>.activeOnly(): List<jt.flights.model.Flight> {
        return filter { flight -> flight.isActive }
    }
}
