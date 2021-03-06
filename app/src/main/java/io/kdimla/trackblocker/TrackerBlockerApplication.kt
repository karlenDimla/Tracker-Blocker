package io.kdimla.trackblocker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.kdimla.trackblocker.trackerdata.source.TrackerDataLoader
import io.kdimla.trackblocker.trackerdata.source.update.TrackerDataUpdateScheduler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


@HiltAndroidApp
class TrackerBlockerApplication : Application(){
    @Inject
    lateinit var trackerDataLoader: TrackerDataLoader
    @Inject
    lateinit var trackerDataUpdateScheduler: TrackerDataUpdateScheduler

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        GlobalScope.launch {
            trackerDataLoader.loadDataDB()
        }.invokeOnCompletion {
            GlobalScope.launch {
                trackerDataLoader.loadMemoryData()
            }
        }
        trackerDataUpdateScheduler.schedulePeriodicUpdate()
    }

}