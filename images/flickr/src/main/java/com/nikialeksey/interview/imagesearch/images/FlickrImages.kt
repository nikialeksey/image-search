package com.nikialeksey.interview.imagesearch.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nikialeksey.interview.imagesearch.images.internal.ImagesDataSourceFactory

class FlickrImages(
    val api: FlickrApi,
    val apiKey: String,
    val pageSize: Int,
    val initialPage: Int
) : Images {

    override fun search(filter: String): ImagesState {
        val progressState = MutableLiveData<ProgressState>()
        val sourceFactory = ImagesDataSourceFactory(
            initialPage,
            if (filter.isEmpty()) {
                { page -> api.getRecent(page, pageSize, apiKey) }
            } else {
                { page -> api.search(filter, page, pageSize, apiKey) }
            },
            progressState
        )
        val images = LivePagedListBuilder(
            sourceFactory,
            pageSize
        ).build()

        return object : ImagesState {
            override fun images(): LiveData<PagedList<Image>> {
                return images
            }

            override fun progress(): LiveData<ProgressState> {
                return progressState
            }

            override fun retry() {
                sourceFactory.source.value?.retry()
            }
        }
    }
}