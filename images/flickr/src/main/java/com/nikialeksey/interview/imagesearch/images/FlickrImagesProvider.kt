package com.nikialeksey.interview.imagesearch.images

import com.nikialeksey.interview.imagesearch.images.impl.BuildConfig
import com.nikialeksey.interview.imagesearch.images.internal.FlickrApi
import com.nikialeksey.interview.imagesearch.images.internal.ImagesImpl
import retrofit2.Retrofit

class FlickrImagesProvider(
    private val retrofit: Retrofit.Builder
) : ImagesProvider {

    override fun images(): Images {
        return ImagesImpl(
            retrofit
                .baseUrl(BuildConfig.FLICKR_ENDPOINT)
                .build()
                .create(FlickrApi::class.java),
            BuildConfig.FLICKR_API_KEY,
            PAGE_SIZE,
            1
        )
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}