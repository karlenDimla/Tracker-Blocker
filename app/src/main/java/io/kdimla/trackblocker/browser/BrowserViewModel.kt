package io.kdimla.trackblocker.browser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.kdimla.trackblocker.browser.webview.RequestUrlProvider

class BrowserViewModel @ViewModelInject constructor(
    private val requestUrlProvider: RequestUrlProvider
) : ViewModel() {
    val searchInputText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val urlToLoad: MutableLiveData<String> by lazy {
        MutableLiveData<String>(requestUrlProvider.getUrl(searchInputText.value))
    }

    fun updateLoadedUrl() {
        urlToLoad.value = requestUrlProvider.getUrl(searchInputText.value)
        searchInputText.value = urlToLoad.value
    }

}
