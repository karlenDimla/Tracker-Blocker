package io.kdimla.trackblocker.trackerdata.source.disconnect

import com.squareup.moshi.Moshi
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.source.TrackerDataParser
import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerEntities
import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerServices
import javax.inject.Inject

class DisconnectEntitiesParser @Inject constructor(
    private val moshi: Moshi
) : TrackerDataParser {

    override fun parseTrackers(rawData: ByteArray): List<Tracker> {
        val rawString = String(rawData)
        val adapter = moshi.adapter(TrackerServices::class.java)
        val result = adapter.fromJson(rawString)
        return result?.trackers ?: listOf()
    }

    override fun parseEntities(rawData: ByteArray): TrackerEntities {
        val rawString = String(rawData)
        val adapter = moshi.adapter(TrackerEntities::class.java)
        val result = adapter.fromJson(rawString)
        return result ?: TrackerEntities(listOf(), listOf())
    }
}



