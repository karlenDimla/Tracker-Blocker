package io.kdimla.trackblocker.trackerdata.db.dao

import androidx.room.*
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty

@Dao
abstract class TrackerPropertyDao {
    @Query("SELECT * FROM tracker_property")
    abstract fun getAll(): List<TrackerProperty>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(properties: List<TrackerProperty>)

    @Query("DELETE FROM tracker_property")
    abstract fun deleteAll()

    @Transaction
    open suspend fun updateAll(properties: List<TrackerProperty>) {
        deleteAll()
        insertAll(properties)
    }

    @Query("SELECT * FROM tracker_property WHERE property = :propertyName ")
    abstract fun getTrackerProperty(propertyName: String): List<TrackerProperty>
}