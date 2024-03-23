package jt.flights.model

import kotlinx.datetime.Instant

public data class Operator(
	val id: Id,
	val name: String,
) {
	@JvmInline
	public value class Id(public val value: String)
}

public data class Flight(
	val id: Id,
	val from: Airport,
	val fromInstant: Instant? = null,
	val to: Airport,
	val toInstant: Instant? = null,
	val isActive: Boolean,
	val flightInfo: Info,
	val operator: Operator? = null,
) {
	public sealed interface Info {
		public data object OnTime : Info
		public data object Delayed : Info
		public data object Cancelled : Info
		public data object Diverted : Info
	}

	@JvmInline
	public value class Id(public val id: String)

	public data class Airport(
		val name: String,
		val iataCode: String,
	)
}

