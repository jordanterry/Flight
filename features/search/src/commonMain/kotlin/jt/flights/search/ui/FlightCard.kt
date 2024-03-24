package jt.flights.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import jt.flights.model.Flight
import jt.flights.model.Operator
import kotlinx.datetime.Instant
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FlightCard(
	flight: Flight,
) {
	Card(
		modifier = Modifier.fillMaxWidth()
			.wrapContentHeight()
			.padding(8.dp),
		shape = CardDefaults.elevatedShape,
		elevation = CardDefaults.cardElevation(3.dp)
	) {
		FlightHeader(flight)
		ToAndFromAirportInfo(
			from = flight.from,
			to = flight.to,
			modifier = Modifier.fillMaxWidth()
		)
	}
}

@Composable
fun FlightHeader(
	flight: Flight,
) {
	Column {
		Row {
			ListItem(
				headlineContent = {
					val operator = flight.operator
					if (operator != null) {
						Text(
							text = operator.name.uppercase(),
							style = MaterialTheme.typography.bodyMedium,
							fontWeight = FontWeight.Bold,
						)
					}
				},
				trailingContent = {
					Text(
						text = flight.id.id.uppercase(),
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Bold,
					)
				},
				colors = ListItemColors(
					containerColor = MaterialTheme.colorScheme.primary,
					headlineColor = MaterialTheme.colorScheme.onPrimary,
					overlineColor = MaterialTheme.colorScheme.onPrimary,
					leadingIconColor = MaterialTheme.colorScheme.onPrimary,
					supportingTextColor = MaterialTheme.colorScheme.onPrimary,
					trailingIconColor = MaterialTheme.colorScheme.onPrimary,
					disabledHeadlineColor = MaterialTheme.colorScheme.onPrimary,
					disabledLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
					disabledTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
				)
			)
		}
	}
}

@Composable
@Preview
fun HeaderPreview() {
	val flight = Flight(
		id = Flight.Id("Vir5c"),
		from = Flight.Airport("London Heathrow", "LHR"),
		to = Flight.Airport("Miami", "MIA"),
		isActive = true,
		flightInfo = Flight.Info.Delayed,
		fromInstant = Instant.parse("2024-02-24T22:15:00Z"),
		operator = Operator(Operator.Id("BA"), "British Airways")
	)
	FlightHeader(flight)
}

@Composable
fun ToAndFromAirportInfo(
	from: Flight.Airport,
	to: Flight.Airport,
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = modifier
			.background(MaterialTheme.colorScheme.background),
		verticalAlignment = Alignment.CenterVertically,
	) {
		StackedAirportInfo(
			airport = from,
			modifier = Modifier.fillMaxWidth().weight(3f),
			horizontalAlignment = Alignment.Start,
		)
		Column(
			modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth().weight(1f),
			verticalArrangement = Arrangement.Center,
		) {
			Text(
				text = "1h 25m",
				style = MaterialTheme.typography.labelMedium,
				color = MaterialTheme.colorScheme.onBackground,
			)
		}
		StackedAirportInfo(
			airport = to,
			modifier = Modifier.fillMaxWidth().weight(3f),
			horizontalAlignment = Alignment.End,
		)
	}
}


@Composable
public fun StackedAirportInfo(
	airport: Flight.Airport,
	modifier: Modifier = Modifier,
	horizontalAlignment:  Alignment.Horizontal,
) {
	Column(
		modifier = modifier
			.background(MaterialTheme.colorScheme.background)
			.padding(16.dp),
		horizontalAlignment = horizontalAlignment
	)  {
		Text(
			text = airport.iataCode,
			style = MaterialTheme.typography.displayLarge,
			color = MaterialTheme.colorScheme.primary,
			fontWeight = FontWeight.Black,
		)
		Text(
			text = airport.name,
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onBackground,
			fontWeight = FontWeight.SemiBold,
		)
	}
}

@Composable
fun StartAlignedStackedAirport(
	airport: Flight.Airport,
	modifier: Modifier = Modifier,
) {
	StackedAirportInfo(
		airport = airport,
		modifier = modifier,
		horizontalAlignment = Alignment.Start,
	)
}
@Composable
fun EndAlignedStackedAirport(
	airport: Flight.Airport,
	modifier: Modifier = Modifier,
) {
	StackedAirportInfo(
		airport = airport,
		modifier = modifier,
		horizontalAlignment = Alignment.End,
	)
}



@Preview
@Composable
fun ToAndFromLHRToLGW() {
	ToAndFromAirportInfo(
		from = Flight.Airport("London Heathrow", "LHR"),
		to = Flight.Airport("London Gatwick", "LGW"),
		modifier = Modifier.wrapContentSize()
	)
}


@Preview
@Composable
fun StartAlignedLondonHeathrow() {
	StartAlignedStackedAirport(
		airport = Flight.Airport("London Heathrow", "LHR"),
		modifier = Modifier.wrapContentSize()
	)
}


@Preview
@Composable
fun EndAlignedLondonHeathrow() {
	EndAlignedStackedAirport(
		airport = Flight.Airport("London Heathrow", "LHR"),
		modifier = Modifier.wrapContentSize()
	)
}

@Preview
@Composable
public fun FlightCardLHRToMIA() {
	val flight = Flight(
		id = Flight.Id("Vir5c"),
		from = Flight.Airport("London Heathrow", "LHR"),
		to = Flight.Airport("Miami", "MIA"),
		isActive = true,
		flightInfo = Flight.Info.Delayed,
		fromInstant = Instant.parse("2024-02-24T22:15:00Z"),
		operator = Operator(Operator.Id("BA"), "British Airways")
	)
	FlightCard(flight = flight)
}