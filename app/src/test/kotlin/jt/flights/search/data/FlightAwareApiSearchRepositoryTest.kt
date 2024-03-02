package jt.flights.search.data

import app.cash.turbine.test
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
        searchRepository.search(
            flightNumber = "1234"
        )

        searchRepository.results.test {
            assertIs<SearchRepository.SearchResults.NotFound>(awaitItem())
        }
    }

    @Test
    fun `Data source throws a failure`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.failure(IOException("Host not found."))
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        searchRepository.search(
            flightNumber = "1234"
        )

        searchRepository.results.test {
            assertIs<SearchRepository.SearchResults.NotFound>(awaitItem())
        }
    }

    @Test
    fun `Data source throws a SerializationException failure`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.failure(SerializationException("Could not serialize field."))
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        searchRepository.search(
            flightNumber = "1234"
        )

        searchRepository.results.test {
            assertIs<SearchRepository.SearchResults.NotFound>(awaitItem())
        }
    }

    @Test
    fun `Data source throws an IllegalArgumentException failure`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.failure(IllegalArgumentException("Not a valid type."))
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        searchRepository.search(
            flightNumber = "1234"
        )
        searchRepository.results.test {
            assertIs<SearchRepository.SearchResults.NotFound>(awaitItem())
        }
    }

    @Test
    fun `Data source throws an OutOfMemoryError failure`() = runTest {
        val dataSource = SearchDataSource { _ ->
            throw OutOfMemoryError("Out of memory!")
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        searchRepository.search(
            flightNumber = "1234"
        )
        searchRepository.results.test {
            assertIs<OutOfMemoryError>(awaitError())
        }
    }

    @Test
    fun `Data source returns a result`() = runTest {
        val dataSource = SearchDataSource { _ ->
            Result.success(
                FlightAwareSearchDataSource.FlightAwareSearchResult.Results(listOf(
                    Flight(
                        id = Flight.Id("1234"),
                        from = Flight.Airport("LHR"),
                        to = Flight.Airport("LCY"),
                        isActive = true,
                        flightInfo = Flight.Info.Delayed,
                    )
                )
            ))
        }
        val searchRepository = FlightAwareSearchRepository(dataSource)
        searchRepository.search(
            flightNumber = "1234"
        )

        searchRepository.results.test {
            val result = awaitItem()
            assertIs<SearchRepository.SearchResults.Found>(result)
            assertTrue(result.results.size == 1)
        }
    }
}