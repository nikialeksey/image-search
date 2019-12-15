package com.nikialeksey.interview.imagesearch.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.Images

class ImagesViewModel(
    images: Images
) : ViewModel() {
    val imageList: LiveData<PagedList<Image>> = LivePagedListBuilder(
        images.resent(),
        images.pageSize()
    ).build()

}