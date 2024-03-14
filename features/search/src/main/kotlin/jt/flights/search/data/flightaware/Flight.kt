package jt.flights.search.data.flightaware

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Flight(
    @SerialName("ident")
    val ident: String,
    @SerialName("origin")
    val origin: Airport,
    @SerialName("destination")
    val destination: Airport,
    @SerialName("aircraft_type")
    val aircraftType: String,
    @SerialName("scheduled_off")
    val scheduledOff: Instant? = null,
    @SerialName("estimated_off")
    val estimatedOff: Instant? = null,
    @SerialName("scheduled_in")
    val scheduledIn: Instant? = null,
    @SerialName("estimated_in")
    val estimatedIn: Instant? = null,

    @SerialName("operator")
    val operator: String,
    @SerialName("operator_icao")
    val operatorIcao: String,
    @SerialName("blocked")
    val blocked: Boolean,
    @SerialName("diverted")
    val diverted: Boolean,
    @SerialName("cancelled")
    val cancelled: Boolean,
    @SerialName("departure_delay")
    val departureDelay: Int? = null,
    @SerialName("arrival_delay")
    val arrivalDelay: Int? = null,
    @SerialName("progress_percent")
    val progressPercent: Int = 0,
)