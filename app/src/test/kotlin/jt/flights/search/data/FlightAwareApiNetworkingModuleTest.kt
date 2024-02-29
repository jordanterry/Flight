package jt.flights.search.data

import org.junit.Test
import kotlin.test.assertEquals

class FlightAwareApiNetworkingModuleTest {
    @Test
    fun `flight aware base url is correct`() {
        val url = FlightAwareNetworkingModule().provideFlightAwareBaseUrl()
        assertEquals("https://aeroapi.flightaware.com/aeroapi", url.toString())
    }
}