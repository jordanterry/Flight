package jt.flights.search

import jt.flights.networking.Network
import jt.flights.networking.OkHttpNetwork
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import java.io.IOException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FlightAwareSearchDataSourceTest {

	private val mockWebServer = MockWebServer()

	@BeforeTest
	fun before() {
		mockWebServer.start()
	}

	@After
	fun after() {
		mockWebServer.shutdown()
	}

	@Test
	fun `network throws an exception during request`() = runTest {
		val network = Network {
			throw IOException("Internet issue!")
		}
		val flightAwareSearchDataSource = FlightAwareSearchDataSource(
			flightAwareBaseUrl = mockWebServer.url("").toString(),
			network = network
		)
		val result = flightAwareSearchDataSource.search("sia321")
		assertTrue(result.isFailure)
		val exception = result.exceptionOrNull()
		assertNotNull(exception)
		assertEquals(exception.message, "Internet issue!")
	}

	@Test
	fun `flight results are found and parsed`() = runTest {
		val network = OkHttpNetwork(OkHttpClient.Builder().build())
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(
					getFile("responses/flights/sia321.json")
				)
		)
		val flightAwareSearchDataSource = FlightAwareSearchDataSource(
			flightAwareBaseUrl = mockWebServer.url("").toString(),
			network = network
		)
		val result = flightAwareSearchDataSource.search("sia321")
		assertTrue(result.isSuccess)
		val request = mockWebServer.takeRequest()
		assertEquals("/flights/sia321", request.requestUrl?.encodedPath)
	}


	@Test
	fun `json body is malformed`() = runTest {
		val network = OkHttpNetwork(OkHttpClient.Builder().build())
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(
					getFile("responses/flights/sia321_malformed.json")
				)
		)
		val flightAwareSearchDataSource = FlightAwareSearchDataSource(
			flightAwareBaseUrl = mockWebServer.url("").toString(),
			network = network
		)
		val result = flightAwareSearchDataSource.search("sia321")
		assertTrue(result.isFailure)
	}

	@Test
	fun `cannot find result for flightNumber`() = runTest {
		val network = OkHttpNetwork(OkHttpClient.Builder().build())
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(
					getFile("responses/flights/sia321_malformed.json")
				)
		)
		val flightAwareSearchDataSource = FlightAwareSearchDataSource(
			flightAwareBaseUrl = mockWebServer.url("").toString(),
			network = network
		)
		val result = flightAwareSearchDataSource.search("ba01")
		assertTrue(result.isFailure)
	}

	private fun getFile(resource: String): String {
		@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
		return this::class.java.classLoader.getResource(resource).readText()
	}
}