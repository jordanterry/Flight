package jt.flights.model

import kotlinx.datetime.Instant

data class Operator(
	val id: Id,
	val name: String,
) {
	@JvmInline
	value class Id(val value: String)
}

data class Flight(
	val id: Id,
	val from: Airport,
	val fromInstant: Instant? = null,
	val to: Airport,
	val toInstant: Instant? = null,
	val isActive: Boolean,
	val flightInfo: Info,
	val operator: Operator? = null,
) {
	sealed interface Info {
		data object OnTime : Info
		data object Delayed : Info
		data object Cancelled : Info
		data object Diverted : Info
	}

	@JvmInline
	value class Id(val id: String)

	data class Airport(
		val name: String,
		val iataCode: String,
	)
}

