package com.nikialeksey.interview.imagesearch

import android.app.Application
import com.nikialeksey.interview.imagesearch.images.Images
import com.nikialeksey.interview.imagesearch.images.ImagesModule
import com.nikialeksey.interview.imagesearch.images.ImagesProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Application : Application(), ImagesProvider {

    private lateinit var imagesModule: ImagesModule

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.FLICKR_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        imagesModule = ImagesModule(
            retrofit,
            BuildConfig.FLICKR_API_KEY,
            20,
            1
        )
    }

    override fun images(): Images {
        return imagesModule.images()
    }
}