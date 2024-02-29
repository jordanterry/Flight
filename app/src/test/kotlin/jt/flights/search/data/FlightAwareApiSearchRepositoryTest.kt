package jt.flights.search.data

import jt.flights.model.Flight
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okio.IOException
import java.lang.IllegalArgumentException
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertIs

class FlightAwareApiSearchRepositoryTest {
    @Test
    fun `Data source returns empty result`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.success(FlightAwareSearchDataSource.FlightAwareSearchResult.NoResults)
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        val result = searchRepository.search(
            flightNumber = "1234"
        )
        assertTrue(result is SearchRepository.SearchResults.NotFound)
    }

    @Test
    fun `Data source throws a failure`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.failure(IOException("Host not found."))
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        val result = searchRepository.search(
            flightNumber = "1234"
        )
        assertTrue(result is SearchRepository.SearchResults.NotFound)
    }

    @Test
    fun `Data source throws a SerializationException failure`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.failure(SerializationException("Could not serialize field."))
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        val result = searchRepository.search(
            flightNumber = "1234"
        )
        assertTrue(result is SearchRepository.SearchResults.NotFound)
    }

    @Test
    fun `Data source throws an IllegalArgumentException failure`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.failure(IllegalArgumentException("Not a valid type."))
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        val result = searchRepository.search(
            flightNumber = "1234"
        )
        assertTrue(result is SearchRepository.SearchResults.NotFound)
    }

    @Test
    fun `Data source throws an OutOfMemoryError failure`() = runTest {
        val dataSource = SearchDataSource { _ ->
            throw OutOfMemoryError("Host not found.")
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        assertFails {
            searchRepository.search(
              flightNumber = "1234"
            )
        }
    }

    @Test
    fun `Data source returns a result`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.success(
                FlightAwareSearchDataSource.FlightAwareSearchResult.Results(listOf(
                    Flight.OnTime(Flight.Id("1234"))))
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