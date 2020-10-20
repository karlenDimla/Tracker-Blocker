package io.kdimla.trackblocker.browser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kdimla.trackblocker.R

class BrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browser_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BrowserFragment.newInstance())
                .commitNow()
        }
    }
}