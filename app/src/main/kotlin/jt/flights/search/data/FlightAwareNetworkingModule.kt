package jt.flights.search.data

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import jt.flights.di.AppScope
import okhttp3.HttpUrl
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
            .addPathSegment("aeroapi/")
            .build()
    }
}

@Qualifier
annotation class FlightAwareBaseUrl