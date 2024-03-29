package jt.flights.search

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import jt.flights.model.Flight

public data object SearchScreen : Screen {

	internal sealed interface UiState : CircuitUiState {
		data object Loading : UiState

		data class Loaded(
			val hint: String,
			val searching: Boolean,
			val search: SearchTerm,
			val pastSearchTerms: List<SearchTerm>,
			val eventSink: (Event) -> Unit,
			val content: Content,
		) : UiState {

			sealed interface Content {
				data class NoResults(
					val message: String,
				) : Content
				data object Initial : Content
				data class Error(val message: String) : Content
				data class Found(
					val flight: Flight
				) : Content
			}
		}
	}

	public sealed interface Event : CircuitUiEvent {
		public data class Search(
			val query: SearchTerm,
		) : Event
		public data class SearchChange(
			val query: SearchTerm,
		) : Event

		public data object OpenSearch : Event
		public data object CloseSearch : Event
	}
}