package jt.flights.flightaware

import okhttp3.Interceptor
import okhttp3.Response

class FlightAwareApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "x-apikey",
                "YJFXnZgeV2byk57xzxzJTNAHruMGfYYH"
            )
            .build()
        return chain.proceed(newRequest)
    }
}