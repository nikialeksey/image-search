package com.nikialeksey.interview.imagesearch.images.internal

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.ProgressState

internal class ImagesDataSource(
    private val progressState: MutableLiveData<ProgressState>,
    private val pageSize: Int,
    private val imagesCount: Int
) : PageKeyedDataSource<Int, Image>() {

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        // ignored
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Image>
    ) {
        progressState.postValue(ProgressState.inProgress())

        val initialPage = 0

        progressState.postValue(ProgressState.success())

        callback.onResult(
            (initialPage until initialPage + pageSize).map(this::toImage),
            null,
            initialPage + 1
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        progressState.postValue(ProgressState.inProgress())

        val page = params.key
        val nextPage = if (page * pageSize < imagesCount) {
            page + 1
        } else {
            null
        }

        progressState.postValue(ProgressState.success())
        callback.onResult(
            (page until page + pageSize).map(this::toImage),
            nextPage
        )
    }

    private fun toImage(number: Int): Image {
        return object : Image {
            override fun thumbnailUrl(): String {
                return "file:///android_asset/$number.jpg"
            }

            override fun largeUrl(): String {
                return "file:///android_asset/$number.jpg"
            }
        }
    }

}