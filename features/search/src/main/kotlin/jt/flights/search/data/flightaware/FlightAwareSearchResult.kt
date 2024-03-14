package jt.flights.search.data.flightaware

sealed interface FlightAwareSearchResult {
    data class Results(
        val flights: List<jt.flights.model.Flight>,
    ): FlightAwareSearchResult
    data object NoResults : FlightAwareSearchResult
    data class ResultFailure(
        val errorCode: Int,
    ) : FlightAwareSearchResult
}
