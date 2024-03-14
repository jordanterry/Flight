package jt.flights.search.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jt.flights.model.Flight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlightsCarousel(
	carouselTitle: String,
	flights: List<Flight>,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier
	) {
		Text(
			modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
			text = carouselTitle
		)
		val pagerState = rememberPagerState { flights.size }
		HorizontalPager(
			state = pagerState,
		) { page ->
			val flight = flights.getOrNull(page)
			if (flight != null) {
				FlightCard(
					modifier = Modifier.padding(horizontal = 16.dp),
					flight = flight,
				)
			}
		}
	}
}