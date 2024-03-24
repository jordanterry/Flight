package jt.flights.flightaware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class FlightAwareResult(
	@SerialName("flights")
	val flightAwareFlights: List<FlightAwareFlight>
)