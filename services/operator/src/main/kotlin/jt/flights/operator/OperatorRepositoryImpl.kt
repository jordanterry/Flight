package jt.flights.arrivals

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.model.Data
import jt.flights.model.Operator
import jt.flights.operator.OperatorDataSource
import jt.flights.operator.OperatorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class OperatorRepositoryImpl @Inject constructor(
	private val operatorDataSource: OperatorDataSource,
) : OperatorRepository {
	override suspend fun details(icao: String): Operator? = withContext(Dispatchers.IO) {
		when (val searchResult = operatorDataSource.search(icao = icao)) {
			is Data.Error -> null
			is Data.None -> null
			is Data.Some -> searchResult.data
		}
	}
}
