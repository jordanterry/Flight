package jt.flights.arrivals

import jt.flights.model.Data
import jt.flights.model.Flight

interface ArrivalsRepository {

	suspend fun search(icao: String): List<Flight>?
}

fun interface AirportArrivalsDataSource {
	suspend fun arrivals(icao: String): Data<List<Flight>>
}