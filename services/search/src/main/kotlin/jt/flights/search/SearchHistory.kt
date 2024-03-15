package jt.flights.search

import android.app.Application
import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.squareup.anvil.annotations.ContributesBinding
import jt.flights.ApplicationContext
import jt.flights.di.AppScope
import javax.inject.Inject

public interface SearchHistoryRepository {
	public suspend fun add(search: String)
}

public interface SearchHistoryDataSource {
	public suspend fun add(search: String)

	public suspend fun get(limit: Int): List<String>
}

@ContributesBinding(AppScope::class)
public class RoomSearchHistoryDataSource @Inject constructor(
	@ApplicationContext private val context: Context,
) : SearchHistoryDataSource {
	private val searchDatabase: AppDatabase = Room.databaseBuilder(
		context,
		AppDatabase::class.java, "database-name"
	).build()

	private val searchDao by lazy {
		searchDatabase.searchDao()
	}

	@Dao
	public interface SearchDao {
		@Query("SELECT * FROM search")
		public fun searchAll(): List<Search>

		@Insert
		public fun insert(search: Search)

		@Delete
		public fun delete(search: Search)

	}

	@Entity
	public data class Search(
		@PrimaryKey(autoGenerate = true) val id: Int,
		@ColumnInfo(name = "search") val search: String,
	)

	override suspend fun add(search: String) {
		searchDao.insert(
			Search(0, search)
		)
	}

	override suspend fun get(limit: Int): List<String> {
		return searchDao.searchAll()
			.take(limit)
			.map { it.search }
	}
}
@Database(entities = [RoomSearchHistoryDataSource.Search::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {
	public abstract fun searchDao(): RoomSearchHistoryDataSource.SearchDao
}