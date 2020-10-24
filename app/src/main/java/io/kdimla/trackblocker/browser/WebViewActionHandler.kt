package io.kdimla.trackblocker.browser

import android.view.KeyEvent

object WebViewActionHandler {
    fun handleBackPress(keyEvent: KeyEvent, doOnBack: () -> Boolean): Boolean {
        return if (keyEvent.action == KeyEvent.ACTION_DOWN) {
            return when (keyEvent.keyCode) {
                KeyEvent.KEYCODE_BACK -> doOnBack()
                else -> false
            }
        } else {
            false
        }
    }

}