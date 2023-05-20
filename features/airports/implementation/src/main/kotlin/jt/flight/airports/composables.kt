package jt.flight.airports

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AirportItem(
    modifier: Modifier = Modifier,
    airport: Airport
) {
    Card(
        modifier
            .padding(8.dp)
    ) {
        Text(text = airport.name)
    }
}

@Preview
@Composable
fun HeathrowAirportItem() {
    AirportItem(airport = Airport("Heathrow", "United Kingdom"))
}