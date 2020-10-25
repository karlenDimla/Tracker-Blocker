package io.kdimla.trackblocker.trackerdata.source.disconnect

import android.content.Context
import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import io.kdimla.trackblocker.trackerdata.source.TrackerDataLoader
import io.kdimla.trackblocker.trackerdata.source.TrackerDataParser
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@WorkerThread
class DisconnectDataLoader @Inject constructor(
    @Named("applicationContext") private val context: Context,
    private val parser: TrackerDataParser,
    private val repository: TrackerDataRepository
) : TrackerDataLoader {
    companion object {
        private const val FILE_PATH_TRACKERS = "disconnect-services.json"
        private const val FILE_PATH_ENTITIES = "disconnect-entities.json"
    }

    override suspend fun loadMemoryData() {
        repository.loadData()
    }

    override suspend fun loadDataDB() {
        if (repository.countTrackers() == 0) loadTrackers()
        if (repository.countProperties() == 0) loadEntities()
    }

    private suspend fun loadTrackers() {
        val result = loadDataFromAsset(FILE_PATH_TRACKERS)
        if (result.isNotEmpty()) {
            val parsedData = parser.parseTrackers(result)
                .filter {
                    // Initially remove content since this causes to block actual websites
                    it.category != "Content"
                }
            repository.saveTrackers(parsedData)
        }
    }

    private suspend fun loadEntities() {
        val result = loadDataFromAsset(FILE_PATH_ENTITIES)
        if (result.isNotEmpty()) {
            val parsedData = parser.parseEntities(result)
            repository.saveProperties(parsedData.properties)
            repository.saveResources(parsedData.resources)
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