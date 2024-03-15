package jt.flights.arrivals

import jt.flights.model.Data
import jt.flights.model.Flight

public interface ArrivalsRepository {

	public suspend fun search(icao: String): List<Flight>?
}

public fun interface AirportArrivalsDataSource {
	public suspend fun arrivals(icao: String): Data<List<Flight>>
}