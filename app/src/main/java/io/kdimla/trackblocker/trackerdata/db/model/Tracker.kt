package io.kdimla.trackblocker.trackerdata.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracker",
    indices = [Index(value = ["url"])]
)
data class Tracker (
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "website") val website: String
) {
    @PrimaryKey(autoGenerate = true) var uid: Long? = null
}