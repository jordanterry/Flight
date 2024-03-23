package jt.flights.search

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

public data class SearchHistory(
	val search: List<SearchTerm>,
)

public interface SearchHistoryRepository {

	public suspend fun get(term: SearchTerm?): List<SearchTerm>

	public suspend fun save(search: SearchTerm)
}

@JvmInline
public value class SearchTerm(public val value: String)

public class SearchHistoryRepositoryImpl(
	private val searchDao: SearchDao,
) : SearchHistoryRepository {
	override suspend fun get(term: SearchTerm?): List<SearchTerm> {
		val results = if (term != null) {
			searchDao.searchByTerm(term.value)
		} else {
			searchDao.getAll().take(10)
		}
		return results.map { SearchTerm(it.search) }
	}

	override suspend fun save(search: SearchTerm) {
		searchDao.insertAll(Search(0, search.value))
	}
}

@Entity
public data class Search(
	@PrimaryKey(autoGenerate = true) val uid: Int,
	@ColumnInfo(name = "search") val search: String,
)
@Dao
public interface SearchDao {
	@Query("SELECT * FROM search")
	public suspend fun getAll(): List<Search>

	@Query("SELECT * FROM search WHERE search LIKE '%' || :search || '%'")
	public suspend fun searchByTerm(search: String): List<Search>

	@Insert
	public suspend fun insertAll(vararg users: Search)
}

@Database(entities = [Search::class], version = 2)
public abstract class SearchDatabase : RoomDatabase() {
	public abstract fun searchDao(): SearchDao
}