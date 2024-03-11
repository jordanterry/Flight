package jt.flights.search.data.flightaware

import kotlinx.serialization.Serializable

@Serializable
internal data class FlightsResult(
    val flights: List<Flight>
)