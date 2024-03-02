package jt.flights.model

data class Flight(
    val id: Id,
    val from: Airport,
    val to: Airport,
    val isActive: Boolean,
    val flightInfo: Info,
) {
    sealed interface Info {
        data object OnTime : Info
        data object Delayed: Info
        data object Cancelled: Info
        data object Diverted: Info
    }

    @JvmInline
    value class Id internal constructor(val id: String)

    data class Airport internal constructor(
        val name: String,
        val iataCode: String,
    )
}

