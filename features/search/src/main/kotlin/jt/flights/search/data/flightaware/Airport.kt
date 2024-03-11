package jt.flights.search.data.flightaware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Airport(
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String,
    @SerialName("code_iata")
    val iataCode: String,
)