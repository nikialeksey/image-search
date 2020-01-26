package com.nikialeksey.interview.imagesearch.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nikialeksey.interview.imagesearch.images.internal.ImagesDataSourceFactory

class FakeImages(
    private val pageSize: Int,
    private val imagesCount: Int = 30 // images count in assets folder
) : Images {
    override fun search(filter: String): ImagesState {
        val progressState = MutableLiveData<ProgressState>()
        val sourceFactory = ImagesDataSourceFactory(
            pageSize,
            imagesCount,
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
                // ignored
            }
        }
    }
}