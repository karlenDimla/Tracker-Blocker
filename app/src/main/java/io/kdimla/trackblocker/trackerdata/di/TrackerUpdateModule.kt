package io.kdimla.trackblocker.trackerdata.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.kdimla.trackblocker.trackerdata.source.TrackerDataDownloader
import io.kdimla.trackblocker.trackerdata.source.disconnect.DisconnectDataDownloader
import io.kdimla.trackblocker.trackerdata.source.endpoint.TrackerDataEndpoint
import io.kdimla.trackblocker.trackerdata.source.update.DefaultTrackerDataUpdateScheduler
import io.kdimla.trackblocker.trackerdata.source.update.TrackerDataUpdateScheduler
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
interface TrackerUpdateModule {
    companion object {
        @Provides
        fun provideWorkManager(
            @Named("applicationContext") context: Context,
            workerFactory: HiltWorkerFactory
        ): WorkManager {
            val config = Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
            WorkManager.initialize(context, config)
            return WorkManager.getInstance(context)
        }

        @Provides
        fun providesRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
            .baseUrl("https://services.disconnect.me/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        @Provides
        fun provideTrackerDataEndpoint(retrofit: Retrofit): TrackerDataEndpoint =
            retrofit.create(TrackerDataEndpoint::class.java)

    }

    @Binds
    fun bindTrackerDataUpdateScheduler(
        impl: DefaultTrackerDataUpdateScheduler
    ): TrackerDataUpdateScheduler

    @Binds
    fun bindTrackerDataDownloader(impl: DisconnectDataDownloader): TrackerDataDownloader
}