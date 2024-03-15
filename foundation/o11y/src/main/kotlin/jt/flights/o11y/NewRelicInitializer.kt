package jt.flights.o11y

import Flights.foundation.o__y.BuildConfig
import android.content.Context
import androidx.startup.Initializer
import com.newrelic.agent.android.NewRelic

public class NewRelicInitializer : Initializer<Unit> {
	override fun create(context: Context) {
		NewRelic
			.withApplicationToken(BuildConfig.NEWRELIC_TOKEN)
			.start(context)
	}

	override fun dependencies(): List<Class<out Initializer<*>>> {
		return emptyList()
	}
}