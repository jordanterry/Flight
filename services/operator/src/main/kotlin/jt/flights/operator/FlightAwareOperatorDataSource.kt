package jt.flights.operator

import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.di.AppScope
import jt.flights.flightaware.FlightAwareBaseUrl
import jt.flights.flightaware.FlightAwareOkHttp
import jt.flights.flightaware.FlightAwareOperator
import jt.flights.model.Data
import jt.flights.model.Operator
import jt.flights.networking.makeRequestAndUseBody
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@ContributesBinding(AppScope::class)
public class FlightAwareOperatorDataSource @Inject constructor(
	private val json: Json,
	@FlightAwareOkHttp private val okHttpClient: OkHttpClient,
	@FlightAwareBaseUrl private val httpUrl: HttpUrl,
) : OperatorDataSource {
	override suspend fun search(icao: String): Data<Operator?> {
		val search = okHttpClient.newCall(urlFromIcao(icao))
		return search.makeRequestAndUseBody { responseBody ->
			json
				.decodeFromString<FlightAwareOperator>(responseBody.string())
				.toOperatorOrNull()
		}
	}

	private fun urlFromIcao(icao: String): Request {
		val searchUrl = httpUrl
			.newBuilder()
			.addPathSegment("operators")
			.addPathSegment(icao)
			.build()
		return Request.Builder()
			.url(searchUrl)
			.get()
			.build()
	}
}

private fun FlightAwareOperator.toOperatorOrNull(): Operator? {
	val icao = icao
	val name = shortName
	if (icao == null || name == null) return null
	return Operator(
		id = Operator.Id(icao),
		name = name
	)
}

