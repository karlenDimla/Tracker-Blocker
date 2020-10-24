package io.kdimla.trackblocker.trackerdata.di

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.kdimla.trackblocker.trackerdata.db.DefaultTrackerDataRepository
import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient
import io.kdimla.trackblocker.trackerdata.source.TrackerDataLoader
import io.kdimla.trackblocker.trackerdata.source.TrackerDataParser
import io.kdimla.trackblocker.trackerdata.source.disconnect.*
import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerEntitiesAdapter
import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerServicesAdapter
import javax.inject.Named
import javax.inject.Singleton

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
    @Singleton
    fun bindTrackerDataLoader(impl: DisconnectDataLoader): TrackerDataLoader

    @Binds
    fun bindTrackerDataDBHelper(impl: DefaultTrackerDataRepository): TrackerDataRepository

    @Binds
    @Named("applicationContext")
    fun bindAppContext(application: Application): Context

    @Binds
    fun bindTrackerDataClient(impl: DisconnectClient): TrackerDataClient

    @Binds
    fun bindThirdPartyDetectorImpl(implDisconnect: DisconnectThirdPartyDetectorImpl): ThirdPartyDetector
}