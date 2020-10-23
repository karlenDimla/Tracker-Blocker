package io.kdimla.trackblocker.trackerdata.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerPropertyDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerResourceDao
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource

@Database(
    entities = [Tracker::class, TrackerResource::class, TrackerProperty::class],
    version = 1,
    exportSchema = true
)
abstract class TrackerBlockerDatabase : RoomDatabase() {
    abstract fun trackerDao(): TrackerDao
    abstract fun trackerResourceDao(): TrackerResourceDao
    abstract fun trackerPropertyDao(): TrackerPropertyDao
}