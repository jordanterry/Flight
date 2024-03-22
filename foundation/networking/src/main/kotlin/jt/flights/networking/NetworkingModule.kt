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
public class NetworkingModule {

	@Provides
	@IntoSet
	public fun httpLoggingInterceptor(): Interceptor {
		return HttpLoggingInterceptor()
	}

	@Provides
	public fun okHttpClient(
		interceptors: Set<@JvmSuppressWildcards Interceptor>,
	): OkHttpClient {
		return OkHttpClient.Builder()
			.apply {
				interceptors.forEach(this::addInterceptor)
			}
			.build()
	}

	@Provides
	public fun provideNetwork(okHttpClient: OkHttpClient): Network {
		return Network { request ->
			okHttpClient
				.newCall(request)
				.await()
		}
	}

	@Provides
	public fun provideJson(): Json {
		return Json {
			ignoreUnknownKeys = true
		}
	}
}


