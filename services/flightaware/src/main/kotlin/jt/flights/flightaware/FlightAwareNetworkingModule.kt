package jt.flights.flightaware

import Flights.services.flightaware.BuildConfig
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import jt.flights.di.AppScope
import jt.flights.networking.Network
import jt.flights.networking.flightaware.FlightAwareApiInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import javax.inject.Qualifier

@Module
@ContributesTo(AppScope::class)
public class FlightAwareNetworkingModule {

	@Provides
	@FlightAwareBaseUrl
	public fun provideFlightAwareBaseUrl(): String {
		return HttpUrl.Builder()
			.scheme("https")
			.host("aeroapi.flightaware.com")
			.addPathSegment("aeroapi")
			.build()
			.toString()
	}
}

@Qualifier
public annotation class FlightAwareBaseUrl

@Qualifier
public annotation class FlightAwareOkHttp