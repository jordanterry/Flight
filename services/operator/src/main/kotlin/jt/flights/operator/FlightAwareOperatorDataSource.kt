package jt.flights.operator

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.flightaware.FlightAwareBaseUrl
import jt.flights.flightaware.FlightAwareOkHttp
import jt.flights.flightaware.FlightAwareOperator
import jt.flights.model.Data
import jt.flights.model.Operator
import jt.flights.networking.Network
import jt.flights.networking.Resource
import jt.flights.networking.fetch
import jt.flights.networking.makeRequestAndUseBody
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.RuntimeException
import javax.inject.Inject

@ContributesBinding(AppScope::class)
public class FlightAwareOperatorDataSource @Inject constructor(
	private val network: Network,
	@FlightAwareBaseUrl private val httpUrl: String,
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

