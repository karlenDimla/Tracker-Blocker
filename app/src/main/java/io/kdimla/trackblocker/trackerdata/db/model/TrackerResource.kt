package io.kdimla.trackblocker.trackerdata.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracker_resource",
    indices = [Index(value = ["resource", "company"])]
)
data class TrackerResource (
    @PrimaryKey
    @ColumnInfo(name = "resource") val resource: String,
    @ColumnInfo(name = "company") val company: String
)