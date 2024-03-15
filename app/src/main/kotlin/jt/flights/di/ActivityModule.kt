package jt.flights.di

import android.app.Activity
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.multibindings.Multibinds

@Module
@ContributesTo(AppScope::class)
public interface ActivityModule {
	@Multibinds
	public fun provideActivityProviders(): Map<Class<out Activity>, Activity>
}
