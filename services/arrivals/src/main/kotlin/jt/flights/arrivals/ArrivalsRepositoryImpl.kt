package jt.flights.arrivals

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.model.Data
import jt.flights.model.Flight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class ArrivalsRepositoryImpl @Inject constructor(
	private val airportArrivalsDataSource: AirportArrivalsDataSource,
) : ArrivalsRepository {
	override suspend fun search(icao: String): List<Flight>? = withContext(Dispatchers.IO) {
		when (val result = airportArrivalsDataSource.arrivals(
			icao = icao
		)) {
			is Data.Error -> null
			is Data.None -> null
			is Data.Some -> result.data
		}
	}
}
