package io.kdimla.trackblocker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.kdimla.trackblocker.trackerdata.source.TrackerDataLoader
import javax.inject.Inject

@HiltAndroidApp
class TrackerBlockerApplication: Application() {
    @Inject lateinit var trackerDataLoader: TrackerDataLoader
    override fun onCreate() {
        super.onCreate()
        trackerDataLoader.loadData()
    }
}