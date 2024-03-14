package jt.flights.arrivals

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.flightaware.FlightAwareSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class FlightAwareArrivalsRepository @Inject constructor(
    private val airportArrivalsDataSource: AirportArrivalsDataSource,
) : ArrivalsRepository {
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
}
