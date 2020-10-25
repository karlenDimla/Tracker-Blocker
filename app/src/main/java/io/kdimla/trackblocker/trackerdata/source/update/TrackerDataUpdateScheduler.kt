package io.kdimla.trackblocker.trackerdata.source.update

import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface TrackerDataUpdateScheduler {
    fun schedulePeriodicUpdate()
}

class DefaultTrackerDataUpdateScheduler @Inject constructor(
    private val workManager: WorkManager
) : TrackerDataUpdateScheduler {
    override fun schedulePeriodicUpdate() {
        workManager.enqueueUniquePeriodicWork(
            TrackerDataUpdateWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            getWorkRequest()
        )
    }

    private fun getWorkRequest(): PeriodicWorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        return PeriodicWorkRequestBuilder<TrackerDataUpdateWorker>(12, TimeUnit.HOURS)
            .addTag(TrackerDataUpdateWorker.TAG)
            .setConstraints(constraints)
            .setInitialDelay(10, TimeUnit.MINUTES)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                10,
                TimeUnit.MINUTES
            )
            .build()
    }

}