package io.kdimla.trackblocker.browser.webview

import android.util.Patterns
import androidx.core.util.PatternsCompat
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

interface RequestUrlProvider {
    fun getUrl(searchText: String?): String
}

class RequestUrlProviderImpl @Inject constructor() : RequestUrlProvider {
    companion object {
        private val webUrlPattern = PatternsCompat.WEB_URL
        private const val DEFAULT_SEARCH_URL = "https://duckduckgo.com/"
        private const val PREFIX_HTTPS = "https://"
        private const val PREFIX_HTTP = "http://"
    }

    override fun getUrl(searchText: String?): String {
        val isUrl = searchText?.isUrl() ?: return DEFAULT_SEARCH_URL
        return if (isUrl) searchText.appendHttps() else "$DEFAULT_SEARCH_URL?q=${searchText.encode()}"
    }

    private fun String.isUrl(): Boolean = webUrlPattern.matcher(this).matches()

    private fun String.encode(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

    private fun String.appendHttps(): String {
        if (startsWith(PREFIX_HTTPS)) return this
        if (startsWith(PREFIX_HTTP)) return replaceFirst(PREFIX_HTTP, PREFIX_HTTPS)
        return "$PREFIX_HTTPS$this"
    }
}