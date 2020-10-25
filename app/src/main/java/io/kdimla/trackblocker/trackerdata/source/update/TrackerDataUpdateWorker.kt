package io.kdimla.trackblocker.trackerdata.source.update

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.kdimla.trackblocker.trackerdata.source.TrackerDataDownloader
import timber.log.Timber

class TrackerDataUpdateWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val trackerDataDownloader: TrackerDataDownloader
): Worker(appContext, workerParams) {
    companion object{
        const val TAG = "update_tracker_data"
    }
    override fun doWork(): Result {
        Timber.tag("Tracker data update").d("Tracker data download initiated")
        return if (trackerDataDownloader.downloadData()) {
            Timber.tag("Tracker data update").d("Tracker data download success")
            Result.success()
        } else {
            Timber.tag("Tracker data update").d("Tracker data download failed")
            Result.retry()
        }
    }
}