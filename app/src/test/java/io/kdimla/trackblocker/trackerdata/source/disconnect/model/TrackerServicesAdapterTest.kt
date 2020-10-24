package io.kdimla.trackblocker.trackerdata.source.disconnect.model

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import org.junit.Before
import org.junit.Test
import java.io.EOFException

class TrackerServicesAdapterTest {
    private lateinit var moshi: Moshi
    private lateinit var adapter: JsonAdapter<TrackerServices>

    @Before
    fun init() {
        moshi = Moshi.Builder()
            .add(TrackerServicesAdapter())
            .build()
        adapter = moshi.adapter<TrackerServices>(TrackerServices::class.java)
    }

    @Test(expected = EOFException::class)
    fun emptyJson_returnEmptyObject() {
        val result = adapter.fromJson("")!!

        assertThat(result.trackers).isEmpty()
    }

    @Test
    fun noEntities_returnEmptyObject() {
        val result = adapter.fromJson(
            "{\n" +
                    "  \"license\": \" \" }"
        )!!

        assertThat(result.trackers).isEmpty()
    }

    @Test
    fun emptyCategories_returnEmptyObject() {
        val result = adapter.fromJson(
            "{\n" +
                    "  \"license\": \"\",\n" +
                    "  \"categories\": {} }"
        )!!

        assertThat(result.trackers).isEmpty()
    }

    @Test
    fun emptyCategory_returnEmptyObject() {
        val result = adapter.fromJson(
            "{\n" +
                    "  \"license\": \"\",\n" +
                    "  \"categories\": {" +
                    "   \"Advertising\": []" +
                    "} }"
        )!!

        assertThat(result.trackers).isEmpty()
    }

    @Test
    fun correctJson_returnObject() {
        val result = adapter.fromJson(
            "{\n" +
                    "  \"license\": \"\",\n" +
                    "  \"categories\": {\n" +
                    "    \"Advertising\": [\n" +
                    "      {\n" +
                    "        \"2leep.com\": {\n" +
                    "          \"http://2leep.com/\": [\n" +
                    "            \"2leep.com\"\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"Test\": [\n" +
                    "      {\n" +
                    "        \"Other.com\": {\n" +
                    "          \"http://Other.com/\": [\n" +
                    "            \"Other.com\",\n" +
                    "            \"Other2.com\"\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}"
        )!!

        assertThat(result.trackers).isEqualTo(
            listOf(
                Tracker("2leep.com", "Advertising", "2leep.com", "http://2leep.com/"),
                Tracker("Other.com", "Test", "Other.com", "http://Other.com/"),
                Tracker("Other2.com", "Test", "Other.com", "http://Other.com/")
            )
        )
    }

    @Test
    fun correctJsonWithTrailingData_returnObjectTrailingIgnored() {
        val result = adapter.fromJson(
            "{\n" +
                    "  \"license\": \"\",\n" +
                    "  \"categories\": {\n" +
                    "    \"Advertising\": [\n" +
                    "      {\n" +
                    "        \"2leep.com\": {\n" +
                    "          \"http://2leep.com/\": [\n" +
                    "            \"2leep.com\"\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"Test\": [\n" +
                    "      {\n" +
                    "        \"2leep.com\": {\n" +
                    "          \"http://2leep.com/\": [\n" +
                    "            \"2leep.com\"\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "\n" +
                    "  \"ignore\": [],\n" +
                    "  \"ignore\": {},\n" +
                    "  \"ignore\": \"ignore\"" +
                    "}"
        )!!

        assertThat(result.trackers).isEqualTo(
            listOf(
                Tracker("2leep.com", "Advertising", "2leep.com", "http://2leep.com/"),
                Tracker("2leep.com", "Test", "2leep.com", "http://2leep.com/")
            )
        )
    }
}