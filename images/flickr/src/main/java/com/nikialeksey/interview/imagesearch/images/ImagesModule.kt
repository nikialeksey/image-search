package com.nikialeksey.interview.imagesearch.images

import com.nikialeksey.interview.imagesearch.images.internal.FlickrApi
import com.nikialeksey.interview.imagesearch.images.internal.ImagesImpl
import retrofit2.Retrofit

class ImagesModule(
    val retrofit: Retrofit,
    val apiKey: String,
    val pageSize: Int,
    val initialPage: Int
) : ImagesProvider {
    override fun images(): Images {
        return ImagesImpl(
            retrofit.create(FlickrApi::class.java),
            apiKey,
            pageSize,
            initialPage
        )
    }
}