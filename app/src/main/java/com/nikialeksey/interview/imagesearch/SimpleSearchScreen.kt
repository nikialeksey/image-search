package com.nikialeksey.interview.imagesearch

import android.os.Bundle
import androidx.navigation.NavController
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.Images
import com.nikialeksey.interview.imagesearch.images.ImagesProvider
import com.nikialeksey.interview.imagesearch.search.Navigation
import com.nikialeksey.interview.imagesearch.search.Screen

class SimpleSearchScreen(
    private val imagesProvider: ImagesProvider
) : Screen {
    override fun images(): Images {
        return imagesProvider.images()
    }

    override fun navigation(): Navigation {
        return object: Navigation {
            override fun openImage(navController: NavController, image: Image) {
                val args = Bundle()
                args.putString("thumbnailUrl", image.thumbnailUrl())
                args.putString("url", image.largeUrl())
                navController.navigate(R.id.action_show_image, args)
            }
        }
    }
}