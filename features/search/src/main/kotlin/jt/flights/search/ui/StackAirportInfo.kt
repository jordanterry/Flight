package jt.flights.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jt.flights.model.Flight
import java.util.Locale

@Composable
fun StackedAirportInfo(
    modifier: Modifier = Modifier,
    airport: Flight.Airport
) {
    Box(
        modifier = modifier
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                fontSize = 48.sp,
                text = airport
                    .iataCode.uppercase(Locale.getDefault())
            )
            Text(text = airport.name)
        }
    }
}

@Preview
@Composable
fun PreviewLondonHeathrow() {
    val airport = Flight.Airport(
        "London Heathrow", "LHR"
    )
    StackedAirportInfo(airport = airport)
}