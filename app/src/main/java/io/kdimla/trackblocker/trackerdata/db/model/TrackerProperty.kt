package io.kdimla.trackblocker.trackerdata.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracker_property",
    indices = [Index(value = ["property"])]
)
data class TrackerProperty (
    @PrimaryKey
    @ColumnInfo(name = "property") val property: String,
    @ColumnInfo(name = "company") val company: String
)