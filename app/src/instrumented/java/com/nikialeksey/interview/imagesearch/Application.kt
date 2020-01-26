package com.nikialeksey.interview.imagesearch

import android.app.Application
import com.nikialeksey.interview.imagesearch.images.FakeImages
import com.nikialeksey.interview.imagesearch.search.App
import com.nikialeksey.interview.imagesearch.search.Screen

class Application : Application(), App {

    private lateinit var searchScreen: Screen

    override fun onCreate() {
        super.onCreate()
        searchScreen = SimpleSearchScreen(FakeImages(10))
    }

    override fun searchScreen(): Screen {
        return searchScreen
    }
}