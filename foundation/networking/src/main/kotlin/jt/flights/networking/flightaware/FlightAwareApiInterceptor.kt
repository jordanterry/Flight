package jt.flights.networking.flightaware

import okhttp3.Interceptor
import okhttp3.Response

public class FlightAwareApiInterceptor(
	private val token: String,
) : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val newRequest = chain.request().newBuilder()
			.addHeader("x-apikey", token)
			.build()
		return chain.proceed(newRequest)
	}
}