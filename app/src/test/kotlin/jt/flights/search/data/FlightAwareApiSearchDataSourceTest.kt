package jt.flights.search.data

import jt.flights.networking.NetworkingModule
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FlightAwareApiSearchDataSourceTest {

    private val mockWebServer = MockWebServer()

    private lateinit var flightAwareSearchDataSource: FlightAwareSearchDataSource

    @BeforeTest
    fun before() {
        mockWebServer.start()

        val networkingModule = NetworkingModule()
        val flightAwareNetworkingModule = FlightAwareNetworkingModule()
        val okHttpClient = flightAwareNetworkingModule
            .provideFlightAwareOkHttpClient(networkingModule.okHttpClient())
        flightAwareSearchDataSource = FlightAwareSearchDataSource(
            okHttpClient = okHttpClient,
            json = networkingModule.provideJson(),
            httpUrl = mockWebServer.url("")
        )
    }

    @AfterTest
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun `200 with flights body`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    getFile("responses/flights/sia321.json")
                )
        )
        val result = flightAwareSearchDataSource.search("1234")
        assertTrue(result.isSuccess)
        assertIs<FlightAwareSearchDataSource.FlightAwareSearchResult.Results>(result.getOrNull())
    }

    @Test
    fun `request contains x-api-key`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
        )
        val result = flightAwareSearchDataSource.search("1234")
        assertNotNull(
            mockWebServer.takeRequest()
                .getHeader("x-apikey")
        )
    }

    @Test
    fun `200 with flights with bad body`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
        )
        val result = flightAwareSearchDataSource.search("1234")
        assertTrue(result.isFailure)
        assertIs<SerializationException>(result.exceptionOrNull())
    }

    @Test
    fun `200 with empty body`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("")
        )
        val result = flightAwareSearchDataSource.search("1234")
        assertTrue(result.isFailure)
        assertIs<SerializationException>(result.exceptionOrNull())
    }

    @Test
    fun `Endpoint returns a 404`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
        )
        val result = flightAwareSearchDataSource.search("1234")
        assertTrue(result.isSuccess)
        val data = result.getOrNull()
        assertIs<FlightAwareSearchDataSource.FlightAwareSearchResult.ResultFailure>(data)
        assertEquals(404, data.errorCode)
    }

    private fun getFile(resource: String): String {
        return this::class.java.classLoader.getResource(resource).readText()
    }

}