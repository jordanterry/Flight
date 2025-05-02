//package jt.flights.search.ui
//
//import app.cash.paparazzi.Paparazzi
//import org.junit.Rule
//import org.junit.Test
//
//class FlightCardTest {
//
//	@get:Rule
//	val paparazzi = Paparazzi()
//
//	@Test
//	fun `Flight card preview lhr to mia`() {
//		paparazzi.snapshot {
//			FlightCardLHRToMIA()
//		}
//	}
//
//	@Test
//	fun `End aligned heathrow`() {
//		paparazzi.snapshot {
//			StartAlignedLondonHeathrow()
//		}
//	}
//
//	@Test
//	fun `Start aligned heathrow`() {
//		paparazzi.snapshot {
//			EndAlignedLondonHeathrow()
//		}
//	}
//
//	@Test
//	fun `To and From London Gatwick`() {
//		paparazzi.snapshot {
//			ToAndFromLHRToLGW()
//		}
//	}
//
//	@Test
//	fun `Header Preview`() {
//		paparazzi.snapshot {
//			HeaderPreview()
//		}
//	}
//}