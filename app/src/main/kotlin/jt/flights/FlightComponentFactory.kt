package jt.flights

import android.app.Application
import androidx.core.app.AppComponentFactory

public class FlightComponentFactory : AppComponentFactory() {

	override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application {
		val app = super.instantiateApplicationCompat(cl, className)
		return app
	}
}