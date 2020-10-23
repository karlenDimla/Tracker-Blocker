package io.kdimla.trackblocker.trackerdata.source

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerEntities

/**
 * Component to use to parse a rawdata string into objects
 */
@WorkerThread
interface TrackerDataParser {
    fun parseTrackers(rawData: ByteArray): List<Tracker>
    fun parseEntities(rawData: ByteArray): TrackerEntities
}