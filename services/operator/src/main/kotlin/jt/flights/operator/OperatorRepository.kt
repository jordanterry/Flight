package jt.flights.operator

import jt.flights.model.Data
import jt.flights.model.Operator

public interface OperatorRepository {
	public suspend fun details(icao: String): Operator?

}


public fun interface OperatorDataSource {
	public suspend fun search(icao: String): Data<Operator?>
}