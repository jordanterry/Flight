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
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

@FormatStringsInDatetimeFormats
@Composable
fun FlightHeader(
    modifier: Modifier = Modifier,
    flight: Flight,
) {
    val header = buildString {
        append(flight.id.id.uppercase())
        append(" ")
        val fromInstant = flight.fromInstant
        if (fromInstant != null) {
            val localDateTime = fromInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val format = localDateTime.format(LocalDateTime.Format {
                byUnicodePattern("yyyy/MM/dd")
            })

//            val format = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(
//                fromInstant.toJavaInstant()
//            )
            append(format)
        }
    }
    ListItem(headlineContent = {
        Text(
            fontWeight = FontWeight.Bold,
            text = header,
            modifier = Modifier
                .padding(16.dp),
        )
    },
        supportingContent = {

        },
        trailingContent = {
            FlightInfo(
                flightInfo = flight.flightInfo
            )
        })
}

@Preview
@Composable
fun HeaderPreview() {
    val flight = Flight(
        id = Flight.Id("Vir5c"),
        from = Flight.Airport("London Heathrow", "LHR"),
        to = Flight.Airport("Miami", "MIA"),
        isActive = true,
        flightInfo = Flight.Info.Delayed,
        fromInstant = Instant.parse("2024-02-24T22:15:00Z")
    )
    FlightHeader(flight = flight)
}