package io.kdimla.trackblocker.browser

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo

object SearchBarActionHandler {

    fun handleEditorAction(actionId: Int, keyEvent: KeyEvent?, doOnSubmit: () -> Boolean): Boolean {
        return handleGo(actionId, doOnSubmit) || handleKeyDown(keyEvent, doOnSubmit)
    }

    private fun handleGo(actionId: Int, doOnSubmit: () -> Boolean): Boolean {
        return when (actionId) {
            EditorInfo.IME_ACTION_GO -> doOnSubmit()
            else -> false
        }
    }

    private fun handleKeyDown(event: KeyEvent?, doOnSubmit: () -> Boolean): Boolean {
        return if (event?.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_ENTER -> doOnSubmit()
                else -> false
            }
        } else {
            false
        }
    }
}