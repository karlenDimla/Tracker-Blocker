package io.kdimla.trackblocker.trackerdata.db

import com.google.common.truth.Truth.assertThat
import io.kdimla.trackblocker.fakes.FakeTrackerDao
import io.kdimla.trackblocker.fakes.FakeTrackerPropertyDao
import io.kdimla.trackblocker.fakes.FakeTrackerResourceDao
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import org.junit.Test

class DefaultTrackerDataRepositoryTest {
    companion object {
        private const val PAGE_URL = "test page url"
        private const val INTERCEPTED_URL = "intercepted url"
    }

    private val fakeTrackerDao = FakeTrackerDao()
    private val fakeTrackerPropertyDao = FakeTrackerPropertyDao()
    private val fakeTrackerResourceDao = FakeTrackerResourceDao()
    private val underTest = DefaultTrackerDataRepository(
        fakeTrackerDao,
        fakeTrackerResourceDao,
        fakeTrackerPropertyDao
    )

    @Test
    fun isTrackerPropertyButNotResource_areUrlsRelated_false() {
        fakeTrackerPropertyDao.expectedPropertyList = listOf(
            TrackerProperty("test", "test")
        )
        fakeTrackerResourceDao.shouldUrlBeAResource = false

        assertThat(underTest.areUrlsRelated(PAGE_URL, INTERCEPTED_URL)).isFalse()
    }

    @Test
    fun isNotTrackerProperty_areUrlsRelated_false() {
        fakeTrackerPropertyDao.expectedPropertyList = listOf()
        fakeTrackerResourceDao.shouldUrlBeAResource = false

        assertThat(underTest.areUrlsRelated(PAGE_URL, INTERCEPTED_URL)).isFalse()
    }

    @Test
    fun isTrackerPropertyAndResource_areUrlsRelated_true() {
        fakeTrackerPropertyDao.expectedPropertyList = listOf(
            TrackerProperty("test", "test")
        )
        fakeTrackerResourceDao.shouldUrlBeAResource = true

        assertThat(underTest.areUrlsRelated(PAGE_URL, INTERCEPTED_URL)).isTrue()
    }

    @Test
    fun counttTrackers_returnDaoSize() {
        fakeTrackerDao.expectedTrackerList = listOf(
            Tracker("test", "test", "test", "test")
        )

        assertThat(underTest.countTrackers()).isEqualTo(1)
    }

    @Test
    fun countProperties_returnDaoSize() {
        fakeTrackerPropertyDao.expectedPropertyList = listOf(
            TrackerProperty("test", "test")
        )

        assertThat(underTest.countProperties()).isEqualTo(1)
    }

}
