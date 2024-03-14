package jt.flights.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jt.flights.model.Flight
import java.util.Locale

@Composable
fun FlightHeader(
    modifier: Modifier = Modifier,
    flight: Flight,
) {
    ListItem(headlineContent = {
        Text(
            fontWeight = FontWeight.Bold,
            text = flight.id.id.uppercase(Locale.getDefault()),
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
        flightInfo = Flight.Info.Delayed
    )
    FlightHeader(flight = flight)
}