package io.kdimla.trackblocker.trackerdata.source

/**
 * Component to use to download tracker data from the data source
 */
interface TrackerDataDownloader {
    fun downloadData()
}