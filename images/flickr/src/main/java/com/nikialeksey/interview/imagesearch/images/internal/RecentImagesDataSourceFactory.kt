package com.nikialeksey.interview.imagesearch.images.internal

import androidx.paging.DataSource
import com.nikialeksey.interview.imagesearch.images.Image

internal class RecentImagesDataSourceFactory(
    val api: FlickrApi,
    val apiKey: String,
    val pageSize: Int,
    val initialPage: Int
) : DataSource.Factory<Int, Image>() {
    override fun create(): DataSource<Int, Image> {
        return RecentImagesDataSource(
            api,
            apiKey,
            pageSize,
            initialPage
        )
    }
}