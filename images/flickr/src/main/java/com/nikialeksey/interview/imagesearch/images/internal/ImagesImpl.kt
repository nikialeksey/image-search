package com.nikialeksey.interview.imagesearch.images.internal

import androidx.paging.DataSource
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.Images

internal class ImagesImpl(
    val api: FlickrApi,
    val apiKey: String,
    val pageSize: Int,
    val initialPage: Int
) : Images {
    override fun resent(): DataSource.Factory<Int, Image> {
        return RecentImagesDataSourceFactory(
            api,
            apiKey,
            pageSize,
            initialPage
        )
    }

    override fun search(filter: String): DataSource.Factory<Int, Image> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pageSize(): Int {
        return pageSize
    }
}