package com.nikialeksey.interview.imagesearch.images.internal

import androidx.paging.DataSource
import com.nikialeksey.interview.imagesearch.images.Image

internal class SearchImagesDataSourceFactory(
    val api: FlickrApi,
    val apiKey: String,
    val filter: String,
    val pageSize: Int,
    val initialPage: Int
) : DataSource.Factory<Int, Image>() {
    override fun create(): DataSource<Int, Image> {
        return SearchImagesDataSource(
            api,
            apiKey,
            filter,
            pageSize,
            initialPage
        )
    }
}