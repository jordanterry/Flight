package jt.flights.model

data class Flight(
    val id: Id,
) {
    @JvmInline
    value class Id internal constructor(val id: String)
}
