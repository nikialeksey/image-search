package com.nikialeksey.interview.imagesearch

import android.app.Application
import com.nikialeksey.interview.imagesearch.images.FlickrApi
import com.nikialeksey.interview.imagesearch.images.FlickrImages
import com.nikialeksey.interview.imagesearch.search.App
import com.nikialeksey.interview.imagesearch.search.Screen
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Application : Application(), App {

    private lateinit var searchScreen: Screen

    override fun onCreate() {
        super.onCreate()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            )

        val images = FlickrImages(
            api = retrofit
                .baseUrl(BuildConfig.FLICKR_ENDPOINT)
                .build()
                .create(FlickrApi::class.java),
            apiKey = BuildConfig.FLICKR_API_KEY,
            pageSize = 20,
            initialPage = 1
        )
        searchScreen = SimpleSearchScreen(images)
    }

    override fun searchScreen(): Screen {
        return searchScreen
    }
}