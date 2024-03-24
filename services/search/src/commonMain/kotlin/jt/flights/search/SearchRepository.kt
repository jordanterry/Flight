package jt.flights.search

import jt.flights.model.Flight

public interface SearchRepository {

	public suspend fun search(flightNumber: SearchTerm): List<Flight>?

}
