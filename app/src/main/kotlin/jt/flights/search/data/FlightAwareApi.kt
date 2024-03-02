package jt.flights.search.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface FlightAwareApi {
    @Serializable
    data class Flight(
        @SerialName("ident")
        val ident: String,
        @SerialName("origin")
        val origin: Airport,
        @SerialName("destination")
        val destination: Airport,
        @SerialName("aircraft_type")
        val aircraftType: String,
        @SerialName("scheduled_off")
        val scheduledOff: String,
        @SerialName("estimated_off")
        val estimatedOff: String,
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

    @Serializable
    data class Airport(
        @SerialName("code")
        val code: String,
        @SerialName("name")
        val name: String,
        @SerialName("code_iata")
        val iataCode: String,
    )

    @Serializable
    data class FlightsResult(
        val flights: List<Flight>
    )
}