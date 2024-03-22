package jt.flights.operator

import jt.flights.flightaware.FlightAwareOperator
import jt.flights.model.Operator
import jt.flights.networking.Network
import jt.flights.networking.Resource
import jt.flights.networking.fetch

public class FlightAwareOperatorDataSource(
	private val network: Network,
	private val httpUrl: String,
) : OperatorDataSource {
	override suspend fun search(icao: String): Result<Operator> {
		return network
			.fetch(
				Resource.operator(httpUrl, icao)
			)
	}
}


internal fun FlightAwareOperator.toOperatorOrNull(): Operator? {
	val icao = icao
	val name = shortName
	if (icao == null || name == null) return null
	return Operator(
		id = Operator.Id(icao),
		name = name
	)
}

