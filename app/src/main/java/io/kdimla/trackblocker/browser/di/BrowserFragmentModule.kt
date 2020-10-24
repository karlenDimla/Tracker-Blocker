package io.kdimla.trackblocker.browser.di

import android.webkit.WebViewClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.kdimla.trackblocker.browser.webview.RequestInterceptor
import io.kdimla.trackblocker.browser.webview.RequestInterceptorImpl
import io.kdimla.trackblocker.trackerdata.source.disconnect.ThirdPartyDetector
import io.kdimla.trackblocker.trackerdata.source.disconnect.DisconnectThirdPartyDetectorImpl
import io.kdimla.trackblocker.browser.webview.RequestUrlProvider
import io.kdimla.trackblocker.browser.webview.RequestUrlProviderImpl
import io.kdimla.trackblocker.browser.webview.TbWebViewClient

/**
 * Dependencies related to TBWebView
 */
@Module
@InstallIn(ActivityComponent::class)
interface BrowserFragmentModule {
    companion object {
        @Provides
        fun provideWebViewClient(requestInterceptor: RequestInterceptor): WebViewClient =
            TbWebViewClient(requestInterceptor)
    }

    @Binds
    fun bindRequestUrlProvider(impl: RequestUrlProviderImpl): RequestUrlProvider

    @Binds
    fun bindRequestInterceptor(impl: RequestInterceptorImpl): RequestInterceptor

}