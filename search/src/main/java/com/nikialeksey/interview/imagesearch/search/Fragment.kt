package com.nikialeksey.interview.imagesearch.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.ImagesProvider
import kotlinx.android.synthetic.main.search_fragment.*


class Fragment : Fragment() {

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ScreenViewModel
    private var adapter = ImagesAdapter()
    private val imagesObserver = Observer<PagedList<Image>> {
        adapter.submitList(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelFactory = ViewModelFactory(
            context.applicationContext as ImagesProvider
        )
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ScreenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.search_fragment,
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_result.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        search_result.adapter = adapter

        viewModel.imagesLive.observe(this, imagesObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.imagesLive.removeObserver(imagesObserver)
    }

    inner class ViewModelFactory(
        private val imagesProvider: ImagesProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (ScreenViewModel::class.java == modelClass) {
                @Suppress("UNCHECKED_CAST")
                ScreenViewModel(imagesProvider.images()) as T
            } else {
                throw IllegalArgumentException(
                    "Unexpected model class: ${modelClass.name}"
                )
            }
        }
    }
}