package io.kdimla.trackblocker.trackerdata.source.disconnect

import android.content.Context
import io.kdimla.trackblocker.trackerdata.source.TrackerDataLoader
import io.kdimla.trackblocker.trackerdata.source.TrackerDataParser
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class DisconnectDataLoader @Inject constructor(
    @Named("applicationContext") private val context: Context,
    private val parser: TrackerDataParser
) : TrackerDataLoader {
    companion object {
        private const val FILE_PATH_TRACKERS = "disconnect-services.json"
        private const val FILE_PATH_ENTITIES = "disconnect-entities.json"
    }

    override fun loadData() {
        loadTrackers()
        loadEntities()
    }

    private fun loadTrackers() {
        val result = loadDataFromAsset(FILE_PATH_TRACKERS)
        if (result.isNotEmpty()) {
            val parsedData = parser.parseTrackers(result)
        }
    }

    private fun loadEntities() {
        val result = loadDataFromAsset(FILE_PATH_ENTITIES)
        if (result.isNotEmpty()) {
            val parsedData = parser.parseEntities(result)
        }
    }

    private fun loadDataFromAsset(filePath: String): ByteArray {
        try {
            val inputStream = context.resources.assets.open(filePath)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            return buffer
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return ByteArray(0)
    }

}