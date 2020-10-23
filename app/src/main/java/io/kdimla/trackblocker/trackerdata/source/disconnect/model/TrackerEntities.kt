package io.kdimla.trackblocker.trackerdata.source.disconnect.model

import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource

class TrackerEntities(
    val properties: List<TrackerProperty>,
    val resources: List<TrackerResource>
)