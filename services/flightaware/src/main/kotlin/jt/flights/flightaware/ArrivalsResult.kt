package jt.flights.flightaware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable




@Serializable
data class ArrivalsResult(
    @SerialName("arrivals")
    val arrivals: List<FlightAwareFlight>
)