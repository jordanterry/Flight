package jt.flights.search.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Search::class], version = 2)
public abstract class SearchDatabase : RoomDatabase() {
	public abstract fun searchDao(): SearchDao
}