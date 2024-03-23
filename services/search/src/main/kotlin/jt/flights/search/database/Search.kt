package jt.flights.search.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
public data class Search(
	@PrimaryKey(autoGenerate = true) val uid: Int,
	@ColumnInfo(name = "search") val search: String,
)