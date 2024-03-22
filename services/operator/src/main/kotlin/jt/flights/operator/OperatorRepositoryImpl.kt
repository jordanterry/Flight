package jt.flights.operator

import jt.flights.model.Operator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class OperatorRepositoryImpl(
	private val operatorDataSource: OperatorDataSource,
) : OperatorRepository {
	override suspend fun details(icao: String): Operator? = withContext(Dispatchers.IO) {
		val searchResult = operatorDataSource.search(icao = icao)
		if (searchResult.isSuccess) {
			searchResult.getOrThrow()
		} else {
			null
		}
	}
}
