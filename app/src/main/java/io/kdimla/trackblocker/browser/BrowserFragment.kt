package io.kdimla.trackblocker.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.kdimla.trackblocker.R

class BrowserFragment : Fragment() {

    companion object {
        fun newInstance() = BrowserFragment()
    }

    private lateinit var viewModel: BrowserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.browser_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BrowserViewModel::class.java)
        // TODO: Use the ViewModel
    }

}