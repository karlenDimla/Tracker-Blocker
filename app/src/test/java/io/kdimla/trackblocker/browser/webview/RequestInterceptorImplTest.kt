package io.kdimla.trackblocker.browser.webview

import com.google.common.truth.Truth.assertThat
import io.kdimla.trackblocker.fakes.FakeThirdPartyDetector
import io.kdimla.trackblocker.fakes.FakeTrackerDataClient
import org.junit.Before
import org.junit.Test

class RequestInterceptorImplTest {
    private val thirdPartyDetector = FakeThirdPartyDetector()
    private val client = FakeTrackerDataClient()

    private val underTest = RequestInterceptorImpl(thirdPartyDetector, client)

    @Before
    fun setUp() {
        client.clearState()
    }

    @Test
    fun urlIsFirstParty_intercept_returnNull() {
        thirdPartyDetector.shouldBeThirdParty = false

        val result = underTest.intercept("pageUrl", "interceptedUrl")

        assertThat(result).isNull()
        assertThat(client.lastState).isEqualTo(FakeTrackerDataClient.State.MatchNotChecked)
    }

    @Test
    fun urlIsThirdPartyButNoMatch_intercept_returnNull() {
        thirdPartyDetector.shouldBeThirdParty = true
        client.shouldMatch = false

        val result = underTest.intercept("pageUrl", "interceptedUrl")

        assertThat(result).isNull()
        assertThat(thirdPartyDetector.paramPageUrl).isEqualTo("pageUrl")
        assertThat(thirdPartyDetector.paramInterceptedUrl).isEqualTo("interceptedUrl")
        assertThat(client.lastState).isEqualTo(
            FakeTrackerDataClient.State.MatchRequested(
                "pageUrl",
                "interceptedUrl"
            )
        )
    }

    @Test
    fun urlIsThirdPartyWithMatch_intercept_returnEmptyResourceResponse() {
        thirdPartyDetector.shouldBeThirdParty = true
        client.shouldMatch = true

        val result = underTest.intercept("pageUrl", "interceptedUrl")

        assertThat(result).isNotNull()
        result?.let {
            assertThat(result.data).isNull()
            assertThat(result.mimeType).isNull()
            assertThat(result.encoding).isNull()
        }
        assertThat(thirdPartyDetector.paramPageUrl).isEqualTo("pageUrl")
        assertThat(thirdPartyDetector.paramInterceptedUrl).isEqualTo("interceptedUrl")
        assertThat(client.lastState).isEqualTo(
            FakeTrackerDataClient.State.MatchRequested(
                "pageUrl",
                "interceptedUrl"
            )
        )
    }
}