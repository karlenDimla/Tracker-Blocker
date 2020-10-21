package io.kdimla.trackblocker.browser.di

import android.webkit.WebViewClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
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
        fun provideWebViewClient(): WebViewClient = TbWebViewClient()
    }

    @Binds
    fun bindRequestUrlProvider(impl: RequestUrlProviderImpl): RequestUrlProvider

}