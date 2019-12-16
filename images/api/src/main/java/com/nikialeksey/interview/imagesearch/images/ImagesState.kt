package com.nikialeksey.interview.imagesearch.images

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

interface ImagesState {
    fun images(): LiveData<PagedList<Image>>
    fun progress(): LiveData<ProgressState>
    fun retry()
}