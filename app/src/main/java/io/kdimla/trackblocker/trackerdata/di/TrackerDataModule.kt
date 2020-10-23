package io.kdimla.trackblocker.trackerdata.di

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.kdimla.trackblocker.trackerdata.source.TrackerDataLoader
import io.kdimla.trackblocker.trackerdata.source.TrackerDataParser
import io.kdimla.trackblocker.trackerdata.db.DefaultTrackerDataDBHelper
import io.kdimla.trackblocker.trackerdata.source.disconnect.DisconnectDataLoader
import io.kdimla.trackblocker.trackerdata.source.disconnect.DisconnectEntitiesParser
import io.kdimla.trackblocker.trackerdata.db.TrackerDataDBHelper
import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient
import io.kdimla.trackblocker.trackerdata.source.disconnect.DisconnectClient
import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerEntitiesAdapter
import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerServicesAdapter
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
interface TrackerDataModule {
    companion object {
        @Provides
        fun provideMoshi(): Moshi = Moshi.Builder()
            .add(TrackerServicesAdapter())
            .add(TrackerEntitiesAdapter())
            .build()

    }

    @Binds
    fun bindTrackerDataParser(impl: DisconnectEntitiesParser): TrackerDataParser

    @Binds
    fun bindTrackerDataLoader(impl: DisconnectDataLoader): TrackerDataLoader

    @Binds
    fun bindTrackerDataDBHelper(impl: DefaultTrackerDataDBHelper): TrackerDataDBHelper

    @Binds
    @Named("applicationContext")
    fun bindAppContext(application: Application): Context

    @Binds
    fun bindTrackerDataClient(impl: DisconnectClient): TrackerDataClient
}