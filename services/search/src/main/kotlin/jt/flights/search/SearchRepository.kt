package jt.flights.search

import jt.flights.model.Data
import jt.flights.model.Flight

public interface SearchRepository {

	public suspend fun search(flightNumber: SearchTerm): List<Flight>?

}

public fun interface SearchDataSource {
	public suspend fun search(flightNumber: SearchTerm): Result<List<Flight>>
}