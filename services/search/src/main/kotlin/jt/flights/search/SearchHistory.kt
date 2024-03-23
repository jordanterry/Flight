package jt.flights.search

public data class SearchHistory(
	val search: List<SearchTerm>,
)

public interface SearchHistoryRepository {
	public suspend fun getAll(): List<SearchTerm>
	public suspend fun getAll(limit: Int): List<SearchTerm>

	public suspend fun get(term: SearchTerm): List<SearchTerm>

	public suspend fun save(search: SearchTerm)
}

@JvmInline
public value class SearchTerm(
	public val value: String
) {
	public fun isEmpty(): Boolean {
		return value.isEmpty()
	}
}
