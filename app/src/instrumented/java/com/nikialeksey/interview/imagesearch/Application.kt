package com.nikialeksey.interview.imagesearch

import android.app.Application
import com.bumptech.glide.Glide
import com.nikialeksey.interview.imagesearch.images.FakeImages

class Application : Application(),
    com.nikialeksey.interview.imagesearch.search.App,
    com.nikialeksey.interview.imagesearch.show.App {

    private lateinit var searchScreen: com.nikialeksey.interview.imagesearch.search.Screen
    private lateinit var showScreen: com.nikialeksey.interview.imagesearch.show.Screen

    override fun onCreate() {
        super.onCreate()
        val glide = Glide.with(this)

        searchScreen = SimpleSearchScreen(FakeImages(10), glide)
        showScreen = SimpleShowScreen(glide)
    }

    override fun searchScreen(): com.nikialeksey.interview.imagesearch.search.Screen {
        return searchScreen
    }

    override fun showScreen(): com.nikialeksey.interview.imagesearch.show.Screen {
        return showScreen
    }
}