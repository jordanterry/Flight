package jt.flights.flightaware

import Flights.services.flightaware.BuildConfig
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import jt.flights.di.AppScope
import jt.flights.networking.flightaware.FlightAwareApiInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import javax.inject.Qualifier

@Module
@ContributesTo(AppScope::class)
class FlightAwareNetworkingModule {

    @Provides
    @FlightAwareBaseUrl
    fun provideFlightAwareBaseUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host("aeroapi.flightaware.com")
            .addPathSegment("aeroapi")
            .build()
    }

    @Provides
    @FlightAwareOkHttp
    fun provideFlightAwareOkHttpClient(
        okHttpClient: OkHttpClient
    ): OkHttpClient {
        return okHttpClient
            .newBuilder()
            .addInterceptor(FlightAwareApiInterceptor(
                token = BuildConfig.FLIGHTAWARE_TOKEN,
            ))
            .build()
    }
}

@Qualifier
public annotation class FlightAwareBaseUrl

@Qualifier
public annotation class FlightAwareOkHttp