package jt.flights.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jt.flights.model.Flight

fun Flight.Info.toCardColors(): CardColors {
    return when(this) {
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
fun FlightCard(
    flight: Flight,
) {
    Box(
        modifier = Modifier
            .padding(8.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = flight.flightInfo.toCardColors(),
            shape = CardDefaults.outlinedShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
        ) {
            Text(
                text = "${flight.from.iataCode} -> ${flight.to.iataCode}",
                modifier = Modifier
                    .padding(16.dp),
            )
        }
    }
}