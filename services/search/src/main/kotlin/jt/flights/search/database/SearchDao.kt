package jt.flights.search.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jt.flights.search.SearchTerm

@Dao
public interface SearchDao {
	@Query("SELECT * FROM search")
	public suspend fun getAll(): List<Search>

	@Query("SELECT * FROM search WHERE search LIKE '%' || :search || '%'")
	public suspend fun searchesByTerm(
		search: String,
	): List<Search>

	@Insert
	public suspend fun insertAll(
		vararg users: Search,
	)
}