package jt.flights.flightaware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class FlightAwareOperator(
	@SerialName("icao")
	val icao: String? = null,
	@SerialName("iata")
	val iata: String? = null,
	@SerialName("callsign")
	val callSign: String? = null,
	@SerialName("name")
	val name: String? = null,
	@SerialName("shortname")
	val shortName: String? = null,
)