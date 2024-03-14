package jt.flights.search

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.squareup.anvil.annotations.ContributesMultibinding
import jt.flights.di.AppScope
import jt.flights.search.SearchScreen
import jt.flights.search.ui.FlightCard
import javax.inject.Inject

class SearchUi : Ui<SearchScreen.UiState> {
    @Composable
    override fun Content(
        state: SearchScreen.UiState,
        modifier: Modifier
    ) {
        Search(state, modifier)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@CircuitInject(SearchScreen::class, AppScope::class)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Search(
    state: SearchScreen.UiState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
    ) {
        var text by remember { mutableStateOf("") }
        var isActive by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .semantics { isTraversalGroup = true }
                    .zIndex(1f)
                    .fillMaxWidth()) {
                SearchBar(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    trailingIcon = {
                       Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    },
                    placeholder = {
                        Text(text = "What's your flight number?")
                    },
                    query = text,
                    onQueryChange = { query ->
                        text = query.uppercase()
                    },
                    onSearch = {
                        state.eventSink(
                            SearchScreen.Event.Search(text)
                        )
                        isActive = false
                    },
                    active = isActive,
                    onActiveChange = { isActive = it }
                ) { }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(18.dp)
            ) {
                items(state.searchResults) { flight ->
                    FlightCard(flight)
                }
            }

            val pagerState = rememberPagerState {
                state.latestArrivals.count()
            }
            HorizontalPager(state = pagerState) { page ->
                val flight = state.latestArrivals.getOrNull(page)
                if (flight != null) {
                    FlightCard(flight = flight)
                }
            }
        }
    }
}