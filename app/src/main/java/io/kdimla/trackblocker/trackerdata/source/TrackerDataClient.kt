package io.kdimla.trackblocker.trackerdata.source

/**
 * Component to use to see if the client matches the given urls
 */
interface TrackerDataClient {
    fun matches(requestedUrl: String, interceptedUrl: String): Boolean
}