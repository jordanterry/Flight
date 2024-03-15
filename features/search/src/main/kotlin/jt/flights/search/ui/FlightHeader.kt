package jt.flights.search.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jt.flights.model.Flight
import jt.flights.model.Operator
import kotlinx.datetime.Instant
import kotlinx.datetime.format.FormatStringsInDatetimeFormats

@Composable
public fun FlightHeader(
	flight: Flight,
) {
	val header = buildString {
		val operator = flight.operator
		if (operator != null) {
			append(operator.name)
		}
	}
	ListItem(headlineContent = {
		Text(
			fontWeight = FontWeight.Bold,
			text = header,
			modifier = Modifier
				.padding(8.dp),
		)
	},
		supportingContent = {

		},
		trailingContent = {

		})
}

@Preview
@Composable
public fun HeaderPreview() {
	val flight = Flight(
		id = Flight.Id("Vir5c"),
		from = Flight.Airport("London Heathrow", "LHR"),
		to = Flight.Airport("Miami", "MIA"),
		isActive = true,
		flightInfo = Flight.Info.Delayed,
		fromInstant = Instant.parse("2024-02-24T22:15:00Z"),
		operator = Operator(Operator.Id("BA"), "British Airways")
	)
	FlightHeader(flight = flight)
}