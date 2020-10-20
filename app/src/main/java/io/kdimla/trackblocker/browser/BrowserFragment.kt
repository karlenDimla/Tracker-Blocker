package io.kdimla.trackblocker.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.core.view.ViewCompat.requireViewById
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.kdimla.trackblocker.R
import io.kdimla.trackblocker.browser.webview.TbWebView
import javax.inject.Inject

@AndroidEntryPoint
class BrowserFragment : Fragment() {

    companion object {
        fun newInstance() = BrowserFragment()
    }

    @Inject
    lateinit var webViewClient: WebViewClient
    private lateinit var searchBar: EditText
    private lateinit var webView: TbWebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.browser_fragment, container, false).also {
            webView = requireViewById(it, R.id.browser_webview)
            searchBar = requireViewById(it, R.id.browser_search_bar)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeWebView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel: BrowserViewModel by activityViewModels()
        // TODO: Use the ViewModel
    }

    private fun initializeWebView() {
        webView.webViewClient = webViewClient
    }

}