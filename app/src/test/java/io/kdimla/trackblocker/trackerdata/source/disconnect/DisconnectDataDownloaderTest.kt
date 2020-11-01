package io.kdimla.trackblocker.trackerdata.source.disconnect

import com.google.common.truth.Truth.assertThat
import io.kdimla.trackblocker.fakes.FakeRepository
import io.kdimla.trackblocker.fakes.FakeTrackerDataEndpointInteractor
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import org.junit.Before
import org.junit.Test

class DisconnectDataDownloaderTest {
    private val fakeRepository = FakeRepository()
    private val endpoint = FakeTrackerDataEndpointInteractor()
    private val underTest = DisconnectDataDownloader(endpoint, fakeRepository)

    @Before
    fun setUp() {
        fakeRepository.clearStateStack()
    }

    @Test
    fun noresult_downloadData_doNothing() {
        endpoint.expectedResult = listOf()

        underTest.downloadData()

        assertThat(fakeRepository.stateStack.isEmpty()).isTrue()
    }

    @Test
    fun withResult_downloadData_doNothing() {
        endpoint.expectedResult = listOf(
            Tracker("test", "test", "test", "test")
        )

        underTest.downloadData()

        assertThat(fakeRepository.stateStack.pop()).isEqualTo(
            FakeRepository.State.TrackersSaved(endpoint.expectedResult)
        )
    }

    @Test
    fun withContent_downloadData_removeContent() {
        endpoint.expectedResult = listOf(
            Tracker("test", "test", "test", "test"),
            Tracker("test", "Content", "test", "test")
        )

        underTest.downloadData()

        assertThat(fakeRepository.stateStack.pop()).isEqualTo(
            FakeRepository.State.TrackersSaved(
                listOf(
                    Tracker("test", "test", "test", "test")
                )
            )
        )
    }
}