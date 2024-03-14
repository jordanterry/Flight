package jt.flights.search

import jt.flights.model.Data
import jt.flights.model.Flight

interface SearchRepository {

	suspend fun search(flightNumber: String): List<Flight>?

}

fun interface SearchDataSource {
	suspend fun search(flightNumber: String): Data<List<Flight>>
}