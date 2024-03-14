package jt.flights.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jt.flights.model.Flight
import java.util.Locale

fun Flight.Info.toCardColors(): CardColors {
    return when (this) {
        Flight.Info.Cancelled -> CardColors(
            contentColor = Color.Black,
            containerColor = Color.Red,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.Red,
        )

        Flight.Info.Delayed -> CardColors(
            contentColor = Color.Black,
            containerColor = Color.Yellow,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.Yellow,
        )

        Flight.Info.Diverted -> CardColors(
            contentColor = Color.Black,
            containerColor = Color.Magenta,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.Magenta,
        )

        Flight.Info.OnTime -> CardColors(
            contentColor = Color.Black,
            containerColor = Color.LightGray,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
        )
    }
}

@Composable
fun FlightInfo(flightInfo: Flight.Info) {
    when (flightInfo) {
        Flight.Info.Cancelled -> CancelledFlightChip()
        Flight.Info.Delayed -> DelayedFlightChip()
        Flight.Info.Diverted -> DivertedFlightChip()
        Flight.Info.OnTime -> OnTimeFlightChip()
    }
}

@Composable
fun FlightCard(
    flight: Flight,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = CardDefaults.elevatedShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
        ) {
            FlightHeader(flight = flight)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                StackedAirportInfo(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .weight(1.0f),
                    airport = flight.from
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .weight(1.0f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "")
                }

                StackedAirportInfo(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .weight(1.0f),
                    airport = flight.to
                )
            }
        }
    }
}

@Preview
@Composable
fun Vir5cPreview() {
    val flight = Flight(
        id = Flight.Id("Vir5c"),
        from = Flight.Airport("London Heathrow", "LHR"),
        to = Flight.Airport("Miami", "MIA"),
        isActive = true,
        flightInfo = Flight.Info.Delayed
    )
    FlightCard(flight = flight)
}