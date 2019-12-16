package com.nikialeksey.interview.imagesearch.images.internal

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.ProgressState
import retrofit2.Call

internal class ImagesDataSourceFactory(
    private val initialPage: Int,
    private val photosResponse: (page: Int) -> Call<PhotosResponse>,
    private val progressState: MutableLiveData<ProgressState>,
    val source: MutableLiveData<ImagesDataSource> = MutableLiveData()
) : DataSource.Factory<Int, Image>() {
    override fun create(): DataSource<Int, Image> {
        val dataSource = ImagesDataSource(
            initialPage,
            photosResponse,
            progressState
        )
        source.postValue(dataSource)
        return dataSource
    }
}