package jt.flights.flightaware

import jt.flights.model.Flight
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlin.time.Duration
import kotlinx.datetime.format.*
import kotlinx.datetime.toDateTimePeriod

public fun FlightAwareFlight.toFlight(): Flight {
	val departureDelay = this.departureDelay
	val arrivalDelay = this.arrivalDelay
	val flightInfo =
		if ((departureDelay != null && departureDelay > 10) || (arrivalDelay != null && arrivalDelay > 10)) {
			Flight.Info.Delayed
		} else if (cancelled) {
			Flight.Info.Cancelled
		} else if (diverted) {
			Flight.Info.Diverted
		} else {
			Flight.Info.OnTime
		}

	val flightTime = if (scheduledIn != null && scheduledOff != null) {
		buildString {
			val result = (scheduledIn - scheduledOff).toDateTimePeriod()
			if (result.days > 0) {
				append(result.days)
				append("d")
				append(" ")
			}
			if (result.hours > 0) {
				append(result.hours)
				append("h")
				append(" ")
			}
			if (result.minutes > 0) {
				append(result.minutes)
				append("m")
				append(" ")
			}
		}
	} else null
	return Flight(
		id = Flight.Id(ident),
		from = Flight.Airport(
			name = origin.name,
			iataCode = origin.iataCode,
		),
		fromInstant = scheduledOff,
		toInstant = scheduledIn,
		to = Flight.Airport(
			name = destination.name,
			iataCode = destination.iataCode,
		),
		isActive = progressPercent in 0..99,
		flightTime = flightTime,
		flightInfo = flightInfo,
	)
}