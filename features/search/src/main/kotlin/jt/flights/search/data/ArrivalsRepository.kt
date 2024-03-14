package jt.flights.search.data

import jt.flights.model.Flight
import kotlinx.coroutines.flow.Flow

interface ArrivalsRepository {

    suspend fun search(flightNumber: String): ArrivalResult

    sealed interface ArrivalResult {
        data class Found(
            val results: List<Flight>
        ) : ArrivalResult

        data object NotFound : ArrivalResult
    }
}
