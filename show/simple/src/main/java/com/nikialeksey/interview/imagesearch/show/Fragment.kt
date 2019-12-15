package com.nikialeksey.interview.imagesearch.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nikialeksey.interview.imagesearch.show.impl.BR
import com.nikialeksey.interview.imagesearch.show.impl.R

class Fragment : Fragment() {
    private lateinit var viewModel: ScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders
            .of(this)
            .get(ScreenViewModel::class.java)
        viewModel.thumbnailUrl = arguments?.getString("thumbnailUrl")
            ?: throw IllegalArgumentException("Unable to open show screen without thumbnail")
        viewModel.url = arguments?.getString("url")
            ?: throw IllegalArgumentException("Unable to open show screen without url")
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.fragment_show,
            container,
            false
        )
        binding.setVariable(
            BR.model,
            viewModel
        )
        binding.lifecycleOwner = this
        return binding.root
    }
}