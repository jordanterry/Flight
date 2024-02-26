package jt.flights.networking

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import jt.flights.di.AppScope
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@ContributesTo(AppScope::class)
class NetworkingModule {

    @Provides
    @IntoSet
    fun httpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
    }

    @Provides
    fun okHttpClient(
//        interceptors: Set<Interceptor>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }
}


