package jt.flights.search.data.flightaware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FlightsResult(
    @SerialName("flights")
    val flights: List<Flight>
)


@Serializable
internal data class ArrivalsResult(
    @SerialName("arrivals")
    val arrivals: List<Flight>
)