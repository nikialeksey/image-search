package com.nikialeksey.interview.imagesearch

import android.app.Application
import com.nikialeksey.interview.imagesearch.images.FlickrApi
import com.nikialeksey.interview.imagesearch.images.FlickrImages
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Application : Application(),
    com.nikialeksey.interview.imagesearch.search.App,
    com.nikialeksey.interview.imagesearch.show.App {

    private lateinit var searchScreen: com.nikialeksey.interview.imagesearch.search.Screen
    private lateinit var showScreen: com.nikialeksey.interview.imagesearch.show.Screen

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

        showScreen = SimpleShowScreen()
    }

    override fun searchScreen(): com.nikialeksey.interview.imagesearch.search.Screen {
        return searchScreen
    }

    override fun showScreen(): com.nikialeksey.interview.imagesearch.show.Screen {
        return showScreen
    }
}