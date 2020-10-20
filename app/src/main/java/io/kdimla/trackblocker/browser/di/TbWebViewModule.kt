package io.kdimla.trackblocker.browser.di

import android.webkit.WebViewClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.kdimla.trackblocker.browser.webview.TbWebViewClient

/**
 * Dependencies related to TBWebView
 */
@Module
@InstallIn(ActivityComponent::class)
object TbWebViewModule {
    @Provides
    fun provideWebViewClient(): WebViewClient = TbWebViewClient()

}