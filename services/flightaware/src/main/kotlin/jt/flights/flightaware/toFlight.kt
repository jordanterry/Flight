package jt.flights.flightaware

import jt.flights.model.Flight

fun FlightAwareFlight.toFlight(): Flight {
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
        isActive = progressPercent in 1..99,
        flightInfo = flightInfo,
    )
}