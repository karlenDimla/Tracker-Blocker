package io.kdimla.trackblocker.trackerdata.db

import com.google.common.truth.Truth.assertThat
import io.kdimla.trackblocker.fakes.FakeTrackerDao
import io.kdimla.trackblocker.fakes.FakeTrackerPropertyDao
import io.kdimla.trackblocker.fakes.FakeTrackerResourceDao
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource
import kotlinx.coroutines.runBlocking
import org.junit.Before
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

    @Before
    fun setUp() {
        fakeTrackerPropertyDao.clearState()
        fakeTrackerDao.clearState()
        fakeTrackerResourceDao.clearState()
    }

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

    @Test
    fun loadData_noDaoCall() {
        runBlocking {
            underTest.loadData()
        }

        assertThat(fakeTrackerDao.stateStack.pop()).isEqualTo(FakeTrackerDao.State.GetAllCalled)
        assertThat(fakeTrackerPropertyDao.stateStack.pop()).isEqualTo(FakeTrackerPropertyDao.State.GetAllCalled)
        assertThat(fakeTrackerResourceDao.stateStack.pop()).isEqualTo(FakeTrackerResourceDao.State.GetAllCalled)
    }

    @Test
    fun memoryDataLoaded_areUrlsRelated_noDaoCall() {
        runBlocking {
            loadDataMemory()
            underTest.loadData()
        }

        underTest.areUrlsRelated(PAGE_URL, INTERCEPTED_URL)

        assertThat(fakeTrackerDao.stateStack).isEmpty()
        assertThat(fakeTrackerPropertyDao.stateStack).isEmpty()
        assertThat(fakeTrackerResourceDao.stateStack).isEmpty()
    }

    @Test
    fun memoryDataLoaded_counttTrackers_noDaoCall() {
        runBlocking {
            loadDataMemory()
            underTest.loadData()
        }

        underTest.countTrackers()

        assertThat(fakeTrackerDao.stateStack).isEmpty()
        assertThat(fakeTrackerPropertyDao.stateStack).isEmpty()
        assertThat(fakeTrackerResourceDao.stateStack).isEmpty()
    }

    @Test
    fun memoryDataLoaded_countProperties_noDaoCall() {
        runBlocking {
            loadDataMemory()
            underTest.loadData()
        }

        underTest.countProperties()

        assertThat(fakeTrackerDao.stateStack).isEmpty()
        assertThat(fakeTrackerPropertyDao.stateStack).isEmpty()
        assertThat(fakeTrackerResourceDao.stateStack).isEmpty()
    }

    @Test
    fun memoryDataLoaded_getTracker_noDaoCall() {
        runBlocking {
            loadDataMemory()
            underTest.loadData()
        }

        underTest.getTracker("something")

        assertThat(fakeTrackerDao.stateStack).isEmpty()
        assertThat(fakeTrackerPropertyDao.stateStack).isEmpty()
        assertThat(fakeTrackerResourceDao.stateStack).isEmpty()
    }

    private fun loadDataMemory() {
        runBlocking {
            underTest.saveTrackers(
                listOf(
                    Tracker("", "", "", "")
                )
            )
            underTest.saveProperties(
                listOf(
                    TrackerProperty("", "")
                )
            )
            underTest.saveResources(
                listOf(
                    TrackerResource("", "")
                )
            )
        }
    }
}
