package io.kdimla.trackblocker.trackerdata.source.update

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.kdimla.trackblocker.trackerdata.source.TrackerDataDownloader

class TrackerDataUpdateWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val trackerDataDownloader: TrackerDataDownloader
): Worker(appContext, workerParams) {
    companion object{
        const val TAG = "update_tracker_data"
    }
    override fun doWork(): Result {
        return if (trackerDataDownloader.downloadData()) {
            Result.success()
        } else {
            Result.retry()
        }
    }
}