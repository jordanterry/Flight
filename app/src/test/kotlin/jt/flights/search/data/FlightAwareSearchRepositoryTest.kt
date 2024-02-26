package jt.flights.search.data

import jt.flights.model.Flight
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs

class FlightAwareSearchRepositoryTest {
    @Test
    fun `Data source returns empty result`() = runTest {
        val dataSource = SearchDataSource { _ -> Result.success(emptyList()) }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        val result = searchRepository.search(
            flightNumber = "1234"
        )
        assertTrue(result is SearchRepository.SearchResults.NotFound)
    }

    @Test
    fun `Data source throws a failure`() = runTest {
        val dataSource = SearchDataSource { _ -> Result.failure(RuntimeException("uh oh")) }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        val result = searchRepository.search(
            flightNumber = "1234"
        )
        assertTrue(result is SearchRepository.SearchResults.NotFound)
    }

    @Test
    fun `Data source returns a result`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.success(
                listOf(Flight(Flight.Id("1234")))
            )
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        val result = searchRepository.search(
            flightNumber = "1234"
        )
        assertIs<SearchRepository.SearchResults.Found>(result)
        assertTrue(result.results.size == 1)
    }
}