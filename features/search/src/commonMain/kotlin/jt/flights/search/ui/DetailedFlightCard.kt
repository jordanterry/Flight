package jt.flights.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import jt.flights.model.Flight
import jt.flights.model.Operator
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun DetailedFlightCard(
	flight: Flight,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
	) {
		Card(
			modifier = Modifier.fillMaxWidth(),
		) {
			ListItem(
				headlineContent = { Text(text = flight.from.name) },
				supportingContent = {
					val time = flight.fromInstant?.formatToFlightTime()
					if (time != null) {
						Text(text = time)
					}
				},
				trailingContent = { Text(text = flight.from.iataCode) },
			)
			ListItem(
				headlineContent = { Text(text = flight.to.name) },
				supportingContent = {
					val time = flight.toInstant?.formatToFlightTime()
					if (time != null) {
						Text(text = time)
					}
				},
				trailingContent = { Text(text = flight.to.iataCode) },
			)
		}
		val operator = flight.operator
		if (operator != null) {
			TertiaryInfoWithOperator(flight.id, operator, flight.flightInfo)
		} else {
			TertiaryInfoWithoutOperator(flight.id, flight.flightInfo)
		}
	}
}

private fun Instant.formatToFlightTime(): String {
	return LocalDateTime.Format {
		hour()
		char(':')
		minute()
	}.format(toLocalDateTime(TimeZone.currentSystemDefault()))
}

@Composable
internal fun TertiaryInfoWithoutOperator(id: Flight.Id, flightInfo: Flight.Info) {
	Card(
		modifier = Modifier.fillMaxWidth()
	) {
		ListItem(
			colors = ListItemColors(
				containerColor = Color(0xFF000000),
				headlineColor = Color.White,
				leadingIconColor = Color.White,
				overlineColor = Color.White,
				supportingTextColor = Color.White,
				trailingIconColor = Color.White,
				disabledHeadlineColor = Color.White,
				disabledLeadingIconColor = Color.White,
				disabledTrailingIconColor = Color.White
			),
			headlineContent = { Text(text = id.id.uppercase()) },
			trailingContent = {
				Text(text = when (flightInfo) {
					Flight.Info.Cancelled -> "Cancelled"
					Flight.Info.Delayed -> "Delayed"
					Flight.Info.Diverted -> "Diverted"
					Flight.Info.OnTime -> "On Time"
				}.uppercase())
			},
		)
	}
}

@Composable
internal fun TertiaryInfoWithOperator(
	id: Flight.Id,
	operator: Operator,
	flightInfo: Flight.Info
) {
//	Card(
//		modifier = Modifier.fillMaxWidth()
//	) {
//		ListItem(
//			colors = ListItemColors(
//				containerColor = Color(0xFFE4181E),
//				headlineColor = Color.White,
//				leadingIconColor = Color.White,
//				overlineColor = Color.White,
//				supportingTextColor = Color.White,
//				trailingIconColor = Color.White,
//				disabledHeadlineColor = Color.White,
//				disabledLeadingIconColor = Color.White,
//				disabledTrailingIconColor = Color.White
//			),
//			headlineContent = { Text(text = operator.name) },
//			supportingContent = { Text(text = id.id.uppercase()) },
//			trailingContent = {
//				Text(text = when (flightInfo) {
//					Flight.Info.Cancelled -> "Cancelled"
//					Flight.Info.Delayed -> "Delayed"
//					Flight.Info.Diverted -> "Diverted"
//					Flight.Info.OnTime -> "On Time"
//				}.uppercase())
//			},
//		)
//	}
}

@Preview
@Composable
internal fun PreviewDetailedWithoutOperator() {
	val flight = Flight(
		id = Flight.Id("Vir5c"),
		from = Flight.Airport("London Heathrow", "LHR"),
		to = Flight.Airport("Miami", "MIA"),
		fromInstant = Instant.DISTANT_PAST,
		toInstant = Instant.DISTANT_FUTURE,
		isActive = true,
		flightInfo = Flight.Info.Delayed
	)
	DetailedFlightCard(flight)
}
@Preview
@Composable
internal fun PreviewDetailedWithOperator() {
	val flight = Flight(
		id = Flight.Id("Vir5c"),
		from = Flight.Airport("London Heathrow", "LHR"),
		to = Flight.Airport("Miami", "MIA"),
		isActive = true,
		flightInfo = Flight.Info.Delayed,
		operator = Operator(Operator.Id("VIR"), "Virgin Atlantic")
	)
	DetailedFlightCard(flight)
}