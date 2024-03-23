package jt.flights.search.usecases

import jt.flights.search.SearchHistoryRepository
import jt.flights.search.SearchTerm

internal fun interface SearchHistoryForTerm {
	suspend operator fun invoke(searchTerm: SearchTerm): List<SearchTerm>
}

internal fun SearchHistoryForTerm(
	searchHistoryRepository: SearchHistoryRepository,
) : SearchHistoryForTerm {
	return SearchHistoryForTerm(searchHistoryRepository::get)
}

internal fun interface AllSearchHistory {
	suspend operator fun invoke(take: Int): List<SearchTerm>
}


internal fun AllSearchHistory(
	searchHistoryRepository: SearchHistoryRepository,
) : AllSearchHistory {
	return AllSearchHistory(searchHistoryRepository::getAll)
}

internal fun interface SaveSearchTerm {
	suspend operator fun invoke(searchTerm: SearchTerm)
}

internal fun SaveSearchTerm(
	searchHistoryRepository: SearchHistoryRepository,
): SaveSearchTerm {
	return SaveSearchTerm(searchHistoryRepository::save)
}