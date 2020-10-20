package io.kdimla.trackblocker.browser.webview

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class TbWebView : WebView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        this.loadUrl("https://www.google.com/")
    }
}