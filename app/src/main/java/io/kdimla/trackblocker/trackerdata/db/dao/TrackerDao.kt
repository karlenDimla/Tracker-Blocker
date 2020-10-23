package io.kdimla.trackblocker.trackerdata.db.dao

import androidx.room.*
import io.kdimla.trackblocker.trackerdata.db.model.Tracker

@Dao
abstract class TrackerDao {
    @Query("SELECT * FROM tracker")
    abstract fun getAll(): List<Tracker>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(trackers: List<Tracker>)

    @Query("DELETE FROM tracker")
    abstract fun deleteAll()

    @Transaction
    open suspend fun updateAll(trackers: List<Tracker>) {
        deleteAll()
        insertAll(trackers)
    }
    @Query("SELECT * FROM tracker WHERE url = :url ")
    abstract fun getTracker(url: String): List<Tracker>

}