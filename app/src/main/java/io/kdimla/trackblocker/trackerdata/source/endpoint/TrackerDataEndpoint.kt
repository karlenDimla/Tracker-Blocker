package io.kdimla.trackblocker.trackerdata.source.endpoint

import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerServices
import retrofit2.Call
import retrofit2.http.GET

interface TrackerDataEndpoint {
    @GET("disconnect-plaintext.json")
    fun getTrackerServices(): Call<TrackerServices>
}