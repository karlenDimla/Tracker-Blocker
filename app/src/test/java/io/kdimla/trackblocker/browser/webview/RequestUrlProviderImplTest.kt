package io.kdimla.trackblocker.browser.webview

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RequestUrlProviderImplTest {

    private val underTest = RequestUrlProviderImpl()

    @Test
    fun nullUrl_getUrl_returnSearchUrl() {
        val result = underTest.getUrl(null)

        assertThat(result).isEqualTo("https://duckduckgo.com/")
    }

    @Test
    fun emptyUrl_getUrl_returnSearchUrl() {
        val result = underTest.getUrl("")

        assertThat(result).isEqualTo("https://duckduckgo.com/?q=")
    }

    @Test
    fun notUrl_getUrl_returnSearchUrlWithQuery() {
        val result = underTest.getUrl("This is not a url")

        assertThat(result).isEqualTo("https://duckduckgo.com/?q=This+is+not+a+url")
    }

    @Test
    fun isUrlNotHttps_getUrl_returnUrlWithHttps() {
        val result = underTest.getUrl("facebook.com")

        assertThat(result).isEqualTo("https://facebook.com")
    }

    @Test
    fun isUrlWithHttps_getUrl_returnUrl() {
        val result = underTest.getUrl("https://facebook.com")

        assertThat(result).isEqualTo("https://facebook.com")
    }

    @Test
    fun isUrlWithHttp_getUrl_returnUrlWithHttps() {
        val result = underTest.getUrl("http://facebook.com")

        assertThat(result).isEqualTo("https://facebook.com")
    }
}