package io.kdimla.trackblocker.trackerdata.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.kdimla.trackblocker.trackerdata.db.TrackerBlockerDatabase
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerPropertyDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerResourceDao

@Module
@InstallIn(ApplicationComponent::class)
class TrackerDatabaseModule {
    @Provides
    fun provideDatabase(application: Application): TrackerBlockerDatabase =
        Room.databaseBuilder(
            application,
            TrackerBlockerDatabase::class.java,
            "tracker-blocker-db"
        ).build()

    @Provides
    fun provideTrackerDao(db: TrackerBlockerDatabase): TrackerDao = db.trackerDao()

    @Provides
    fun provideTrackerPropertyDao(
        db: TrackerBlockerDatabase
    ): TrackerPropertyDao = db.trackerPropertyDao()

    @Provides
    fun provideTrackerResourceDao(
        db: TrackerBlockerDatabase
    ): TrackerResourceDao = db.trackerResourceDao()
}