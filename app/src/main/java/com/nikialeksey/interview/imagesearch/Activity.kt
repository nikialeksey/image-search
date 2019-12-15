package com.nikialeksey.interview.imagesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.search.SearchNavigation

class Activity : AppCompatActivity(R.layout.activity), SearchNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
    }

    override fun openImage(navController: NavController, image: Image) {
        val args = Bundle()
        args.putString("thumbnailUrl", image.thumbnailUrl())
        args.putString("url", image.largeUrl())
        navController.navigate(R.id.action_show_image, args)
    }
}