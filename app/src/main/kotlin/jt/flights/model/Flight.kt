package jt.flights.model

sealed interface Flight {

    data class OnTime(
        val id: Flight.Id,
    ) : Flight

    data class Delayed(
        val id: Flight.Id,
    ) : Flight

    data class Cancelled(
        val id: Flight.Id,
    ) : Flight

    data class Diverted(
        val id: Flight.Id,
    ) : Flight

    @JvmInline
    value class Id internal constructor(val id: String)
}