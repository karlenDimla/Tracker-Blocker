package io.kdimla.trackblocker.trackerdata.source.disconnect.model

import com.squareup.moshi.*
import io.kdimla.trackblocker.trackerdata.db.model.Tracker

class TrackerServicesAdapter : JsonAdapter<TrackerServices>() {
    @FromJson
    override fun fromJson(reader: JsonReader): TrackerServices? {
        val list = mutableListOf<Tracker>()
        while (reader.hasNext()) {
            reader.beginObject()
            while (reader.selectName(JsonReader.Options.of("categories")) != 0 && reader.hasNext()) {
                reader.skipValue()
            }
            // we are in categories
            while (reader.hasNext() && reader.peek().name.equals("BEGIN_OBJECT")) {
                reader.beginObject()
                while (reader.hasNext()) {
                    val category = reader.nextName()
                    reader.beginArray()
                    while (reader.hasNext()) {
                        reader.beginObject()
                        val company = reader.nextName()
                        reader.beginObject()
                        val website = reader.nextName()
                        reader.beginArray()
                        while (reader.hasNext()) {
                            list.add(
                                Tracker(
                                    url = reader.nextString(),
                                    category = category,
                                    company = company,
                                    website = website
                                )
                            )
                        }
                        reader.endArray()
                        while (reader.hasNext()) {
                            reader.skipValue()
                        }
                        reader.endObject()
                        reader.endObject()
                    }
                    reader.endArray()
                }
                reader.endObject()
            }
            while (reader.hasNext()) {
                reader.skipValue()
            }
            reader.endObject()
        }
        return TrackerServices(list.toList())
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: TrackerServices?) {
        throw UnsupportedOperationException("TrackerServicesAdapter is only used to deserialize objects")
    }
}