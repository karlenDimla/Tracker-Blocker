package io.kdimla.trackblocker.trackerdata.source

import androidx.annotation.WorkerThread

/**
 * Class for loading the data from a file and persists it for usage
 */
@WorkerThread
interface TrackerDataLoader {
    suspend fun loadData()
}