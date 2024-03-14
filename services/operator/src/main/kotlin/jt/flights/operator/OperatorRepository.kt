package jt.flights.operator

import jt.flights.model.Data
import jt.flights.model.Operator

interface OperatorRepository {
	suspend fun details(icao: String): Operator?

}


fun interface OperatorDataSource {
	suspend fun search(icao: String): Data<Operator?>
}