package io.kdimla.trackblocker.browser.webview

import android.util.Patterns
import javax.inject.Inject

interface RequestUrlProvider {
    fun getUrl(searchText: String?): String
}

class RequestUrlProviderImpl @Inject constructor() : RequestUrlProvider {
    companion object {
        private val webUrlPattern = Patterns.WEB_URL
        private const val DEFAULT_SEARCH_URL = "https://duckduckgo.com/"
        private const val PREFIX_HTTPS = "https://"
        private const val PREFIX_HTTP = "http://"
    }

    override fun getUrl(searchText: String?): String {
        val isUrl = searchText?.isUrl() ?: return DEFAULT_SEARCH_URL
        return if (isUrl) searchText.appendHttps() else "$DEFAULT_SEARCH_URL?q=$searchText"
    }

    private fun String.isUrl(): Boolean = webUrlPattern.matcher(this).matches()

    private fun String.appendHttps(): String {
        if (startsWith(PREFIX_HTTPS)) return this
        if (startsWith(PREFIX_HTTP)) return replaceFirst(PREFIX_HTTP, PREFIX_HTTPS)
        return "$PREFIX_HTTPS$this"
    }
}