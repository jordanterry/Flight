package jt.flights.search

import jt.flights.Searches

private class SearchHistoryRepositoryImpl(
	private val searchHistoryDataSource: SearchHistoryDataSource,
) : SearchHistoryRepository {
	override suspend fun getAll(): List<SearchTerm> {
		return searchHistoryDataSource.getAll()
	}

	override suspend fun getAll(limit: Int): List<SearchTerm> {
		return searchHistoryDataSource.get(limit)
	}

	override suspend fun get(term: SearchTerm): List<SearchTerm> {
		return searchHistoryDataSource.getForTerm(term)
	}

	override suspend fun save(search: SearchTerm) {
		searchHistoryDataSource.save(search)
	}
}

public fun SearchHistoryRepository(
	searchesDb: Searches
): SearchHistoryRepository {
	return SearchHistoryRepositoryImpl(DbSearchHistoryDataSource(searchesDb))
}