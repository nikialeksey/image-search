package com.nikialeksey.interview.imagesearch.images.internal

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.ProgressState

internal class ImagesDataSourceFactory(
    private val pageSize: Int,
    private val imagesCount: Int,
    private val progressState: MutableLiveData<ProgressState>,
    val source: MutableLiveData<ImagesDataSource> = MutableLiveData()
) : DataSource.Factory<Int, Image>() {
    override fun create(): DataSource<Int, Image> {
        val dataSource = ImagesDataSource(
            progressState,
            pageSize,
            imagesCount
        )
        source.postValue(dataSource)
        return dataSource
    }
}