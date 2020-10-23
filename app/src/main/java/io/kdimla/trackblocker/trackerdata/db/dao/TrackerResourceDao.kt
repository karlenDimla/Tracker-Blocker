package io.kdimla.trackblocker.trackerdata.db.dao

import androidx.room.*
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource

@Dao
abstract class TrackerResourceDao {
    @Query("SELECT * FROM tracker_resource")
    abstract fun getAll(): List<TrackerResource>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(resources: List<TrackerResource>)

    @Query("DELETE FROM tracker_resource")
    abstract fun deleteAll()

    @Transaction
    open suspend fun updateAll(resources: List<TrackerResource>) {
        deleteAll()
        insertAll(resources)
    }

    @Query("SELECT * FROM tracker_resource WHERE resource = :resourceName AND company" +
            "= :companyName")
    abstract fun countTrackerResource(resourceName: String, companyName:String): Int
}