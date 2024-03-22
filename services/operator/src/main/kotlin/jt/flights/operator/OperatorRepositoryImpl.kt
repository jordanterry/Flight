package jt.flights.operator

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.model.Data
import jt.flights.model.Operator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
public class OperatorRepositoryImpl @Inject constructor(
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
