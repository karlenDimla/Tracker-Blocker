package io.kdimla.trackblocker.trackerdata.source.disconnect.model

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource
import org.junit.Before
import org.junit.Test
import java.io.EOFException

class TrackerEntitiesAdapterTest {
    private lateinit var moshi: Moshi
    private lateinit var adapter: JsonAdapter<TrackerEntities>

    @Before
    fun init() {
        moshi = Moshi.Builder()
            .add(TrackerEntitiesAdapter())
            .build()
        adapter = moshi.adapter<TrackerEntities>(TrackerEntities::class.java)
    }

    @Test(expected = EOFException::class)
    fun emptyJson_returnEmptyObject() {
        val result = adapter.fromJson("")!!

        assertThat(result.properties).isEmpty()
        assertThat(result.resources).isEmpty()
    }

    @Test
    fun noEntities_returnEmptyObject() {
        val result = adapter.fromJson("{\n" +
                "  \"license\": \" \" }")!!

        assertThat(result.properties).isEmpty()
        assertThat(result.resources).isEmpty()
    }

    @Test
    fun emptyEntities_returnEmptyObject() {
        val result = adapter.fromJson("{\n" +
                "  \"license\": \"\",\n" +
                "  \"entities\": {} }")!!

        assertThat(result.properties).isEmpty()
        assertThat(result.resources).isEmpty()
    }

    @Test
    fun withCorrectEntities_returnTrackerEntities() {
        val result = adapter.fromJson("{\n" +
                "  \"license\": \"\",\n" +
                "  \"entities\": {\n" +
                "    \"2leep.com\": {\n" +
                "      \"properties\": [\n" +
                "        \"2leep.com\"\n" +
                "      ],\n" +
                "      \"resources\": [\n" +
                "        \"2leep.com\"\n" +
                "      ]\n" +
                "    } } }")!!

        assertThat(result.properties).isEqualTo(listOf(
            TrackerProperty("2leep.com","2leep.com")
        ))
        assertThat(result.resources).isEqualTo(listOf(
            TrackerResource("2leep.com","2leep.com")
        ))
    }

    @Test
    fun noProperty_returnEmptyObject_emptyProperties() {
        val result = adapter.fromJson("{\n" +
                "  \"license\": \"\",\n" +
                "  \"entities\": {\n" +
                "    \"2leep.com\": {\n" +
                "      \"resources\": [\n" +
                "        \"2leep.com\"\n" +
                "      ]\n" +
                "    } } }")!!

        assertThat(result.properties).isEmpty()
        assertThat(result.resources).isEqualTo(listOf(
            TrackerResource("2leep.com","2leep.com")
        ))
    }
    @Test
    fun noResource_returnEmptyObject_emptyResource() {
        val result = adapter.fromJson("{\n" +
                "  \"license\": \"\",\n" +
                "  \"entities\": {\n" +
                "    \"2leep.com\": {\n" +
                "      \"properties\": [\n" +
                "        \"2leep.com\"\n" +
                "      ] } } }")!!

        assertThat(result.properties).isEqualTo(listOf(
            TrackerProperty("2leep.com","2leep.com")
        ))
        assertThat(result.resources).isEmpty()
    }

    @Test
    fun withMultipleEntities_returnEmptyObject_returnObject() {
        val result = adapter.fromJson("{\n" +
                "  \"license\": \"\",\n" +
                "  \"entities\": {\n" +
                "    \"2leep.com\": {\n" +
                "      \"properties\": [\n" +
                "        \"2leep.com\",\n" +
                "        \"Other.com\"\n" +
                "      ],\n" +
                "      \"resources\": [\n" +
                "        \"2leep.com\",\n" +
                "        \"Other.com\"\n" +
                "      ]\n" +
                "    } } }")!!

        assertThat(result.properties).isEqualTo(listOf(
            TrackerProperty("2leep.com","2leep.com"),
            TrackerProperty("Other.com","2leep.com")
        ))
        assertThat(result.resources).isEqualTo(listOf(
            TrackerResource("2leep.com","2leep.com"),
            TrackerResource("Other.com","2leep.com")
        ))
    }
}