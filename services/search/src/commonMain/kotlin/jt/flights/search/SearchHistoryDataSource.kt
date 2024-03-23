package jt.flights.search

import jt.flights.Searches

internal interface SearchHistoryDataSource {
	suspend fun save(searchTerm: SearchTerm)

	suspend fun get(limit: Int): List<SearchTerm>
	suspend fun getAll(): List<SearchTerm>

	suspend fun getForTerm(searchTerm: SearchTerm): List<SearchTerm>
}

public class DbSearchHistoryDataSource(
	private val searches: Searches,
) : SearchHistoryDataSource {
	override suspend fun save(searchTerm: SearchTerm) {
		searches.searchQueries.insert(searchTerm.value)
	}

	override suspend fun get(limit: Int): List<SearchTerm> {
		return searches.searchQueries.selectAll { _, search ->
			SearchTerm(search)
		}.executeAsList().take(limit)
	}

	override suspend fun getAll(): List<SearchTerm> {
		return searches.searchQueries.selectAll { _, search ->
			SearchTerm(search)
		}.executeAsList()
	}

	override suspend fun getForTerm(searchTerm: SearchTerm): List<SearchTerm> {
		return searches.searchQueries.selectByTerm(searchTerm.value) { _, search ->
			SearchTerm(search)
		}.executeAsList()
	}
}