package jt.flights.model

sealed interface Flight {

    data class OnTime(
        val id: Id,
    ) : Flight

    data class Delayed(
        val id: Id,
    ) : Flight

    data class Cancelled(
        val id: Id,
    ) : Flight

    data class Diverted(
        val id: Id,
    ) : Flight

    @JvmInline
    value class Id internal constructor(val id: String)
}