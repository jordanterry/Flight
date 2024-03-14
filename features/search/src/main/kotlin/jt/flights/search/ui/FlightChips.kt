package jt.flights.search.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Immutable
class FlightChipColor(
    val containerColor: Color,
    val borderColor: Color,
    val labelColor: Color,
)

object FlightChipColours {
    val onTime = FlightChipColor(
        containerColor = Color.Green,
        borderColor = Color.Green,
        labelColor = Color.Black,
    )
    val delayed = FlightChipColor(
        containerColor = Color.Yellow,
        borderColor = Color.Yellow,
        labelColor = Color.Black,
    )
    val cancelled = FlightChipColor(
        containerColor = Color.Red,
        borderColor = Color.Red,
        labelColor = Color.White,
    )
    val diverted = FlightChipColor(
        containerColor = Color.Black,
        borderColor = Color.Black,
        labelColor = Color.White,
    )
}

@Composable
fun FlightChip(
    text: String,
    flightChipColor: FlightChipColor,
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .border(
                shape = shape,
                border = BorderStroke(2.dp, flightChipColor.borderColor),
            )
            .background(flightChipColor.containerColor, shape)
    ) {
        Text(
            text.uppercase(),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
            color = flightChipColor.labelColor
        )
    }
}

@Composable
fun DelayedFlightChip() {
    FlightChip(
        text = "Delayed",
        flightChipColor = FlightChipColours.delayed
    )
}

@Composable
fun OnTimeFlightChip() {
    FlightChip(
        text = "On Time",
        flightChipColor = FlightChipColours.onTime
    )
}


@Composable
fun CancelledFlightChip() {
    FlightChip(
        text = "Cancelled",
        flightChipColor = FlightChipColours.cancelled
    )
}

@Composable
fun DivertedFlightChip() {
    FlightChip(
        text = "Diverted",
        flightChipColor = FlightChipColours.diverted
    )
}

@Preview
@Composable
fun OnTimePreview() {
    OnTimeFlightChip()
}

@Preview
@Composable
fun DivertedPreview() {
    DivertedFlightChip()
}

@Preview
@Composable
fun CancelledPreview() {
    CancelledFlightChip()
}

@Preview
@Composable
fun DelayedPreview() {
    CancelledFlightChip()
}