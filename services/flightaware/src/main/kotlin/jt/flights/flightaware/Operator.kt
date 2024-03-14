package jt.flights.flightaware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Operator(
	@SerialName("code")
	val code: String,
	@SerialName("name")
	val name: String,
	@SerialName("code_iata")
	val iataCode: String,
)