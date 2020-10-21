package io.kdimla.trackblocker.browser

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint
import io.kdimla.trackblocker.R
import io.kdimla.trackblocker.databinding.BrowserFragmentBinding
import javax.inject.Inject


@AndroidEntryPoint
class BrowserFragment : Fragment() {

    companion object {
        fun newInstance() = BrowserFragment()
    }

    @Inject
    lateinit var webViewClient: WebViewClient
    private lateinit var fragmentBinding: BrowserFragmentBinding
    private val viewModel: BrowserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.browser_fragment,
            container,
            false
        )
        fragmentBinding.browseViewModel = viewModel
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.browserWebview.webViewClient = webViewClient
        fragmentBinding.browserSearchBar.setOnEditorActionListener { view, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_GO -> {
                    viewModel.updateLoadedUrl()
                    val imm =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    view.clearFocus()
                    true
                }
                else -> false
            }
        }
        viewModel.urlToLoad.observe(viewLifecycleOwner) {
            fragmentBinding.browserWebview.loadUrl(it)
        }
    }

}