package io.kdimla.trackblocker.trackerdata.source.disconnect.model

import com.squareup.moshi.*
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource

class TrackerEntitiesAdapter : JsonAdapter<TrackerEntities>() {
    @FromJson
    override fun fromJson(reader: JsonReader): TrackerEntities? {
        val resources = mutableListOf<TrackerResource>()
        val properties = mutableListOf<TrackerProperty>()

        while (reader.hasNext()) {
            reader.beginObject()
            while (reader.selectName(JsonReader.Options.of("entities")) != 0 && reader.hasNext()) {
                reader.skipValue()
            }
            // we are in entities
            while (reader.hasNext()) {
                reader.beginObject()
                while (reader.hasNext()) {
                    val company = reader.nextName()
                    reader.beginObject()
                    while (reader.hasNext()) {
                        if (reader.selectName(JsonReader.Options.of("properties")) == 0) {
                            reader.beginArray()
                            while (reader.hasNext()) {
                                properties.add(TrackerProperty(reader.nextString(), company))
                            }
                            reader.endArray()
                        } else if (reader.selectName(JsonReader.Options.of("resources")) == 0) {
                            reader.beginArray()
                            while (reader.hasNext()) {
                                resources.add(TrackerResource(reader.nextString(), company))
                            }
                            reader.endArray()
                        } else {
                            reader.skipValue()
                        }
                    }
                    reader.endObject()
                }
                reader.endObject()
            }
            reader.endObject()
        }
        return TrackerEntities(properties.toList(), resources.toList())
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: TrackerEntities?) {
        throw UnsupportedOperationException("TrackerEntitiesAdapter is only used to deserialize objects")
    }
}