package com.nikialeksey.interview.imagesearch.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.Images
import com.nikialeksey.interview.imagesearch.images.ImagesProvider
import kotlinx.android.synthetic.main.search_fragment.*
import java.lang.IllegalArgumentException


class Fragment : Fragment(R.layout.search_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ImagesAdapter()
        search_result.layoutManager = GridLayoutManager(context, 2)
        search_result.adapter = adapter

        val images: Images = (view.context.applicationContext as ImagesProvider).images()
        val imagesViewModel = ViewModelProviders
            .of(
                this,
                object: ViewModelProvider.Factory {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        return if (ImagesViewModel::class.java == modelClass) {
                            @Suppress("UNCHECKED_CAST")
                            ImagesViewModel(images) as T
                        } else {
                            throw IllegalArgumentException("Unexpected model class: ${modelClass.name}")
                        }
                    }
                }
            )
            .get(ImagesViewModel::class.java)

        imagesViewModel.imageList.observe(
            this,
            Observer<PagedList<Image>> { adapter.submitList(it) }
        )
    }
}