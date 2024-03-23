package jt.flights.flightaware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class FlightsResult(
	@SerialName("flights")
	val flightAwareFlights: List<FlightAwareFlight>
)