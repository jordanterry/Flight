package jt.flights.search.usecases

import app.cash.sqldelight.db.SqlDriver
import jt.flights.search.SearchHistoryRepository
import jt.flights.search.SearchTerm
import jt.flights.search.database.Search
import jt.flights.search.database.SearchDao

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