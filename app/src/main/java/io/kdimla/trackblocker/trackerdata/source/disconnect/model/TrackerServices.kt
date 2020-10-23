package io.kdimla.trackblocker.trackerdata.source.disconnect.model

import io.kdimla.trackblocker.trackerdata.db.model.Tracker

data class TrackerServices(
    val trackers: List<Tracker>
)
