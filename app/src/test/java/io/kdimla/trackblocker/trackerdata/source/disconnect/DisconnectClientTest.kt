package io.kdimla.trackblocker.trackerdata.source.disconnect

import com.google.common.truth.Truth.assertThat
import io.kdimla.trackblocker.fakes.FakeRepository
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import org.junit.Test

class DisconnectClientTest {
    private val fakeRepository = FakeRepository()
    private val underTest = DisconnectClient(fakeRepository)

    @Test
    fun urlMatches_matches_true() {
        fakeRepository.expectedTrackerList = listOf(
            Tracker("test", "test", "test", "test")
        )
        assertThat(underTest.matches("pageUrl.com", "interceptedUrl.com")).isTrue()
    }

    @Test
    fun urlNoMatch_matches_false() {
        fakeRepository.expectedTrackerList = listOf()
        assertThat(underTest.matches("pageUrl.com", "interceptedUrl.com")).isFalse()
    }
}