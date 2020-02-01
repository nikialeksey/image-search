package com.nikialeksey.interview.imagesearch

import android.os.Bundle
import androidx.navigation.NavController
import com.bumptech.glide.RequestManager
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.Images
import com.nikialeksey.interview.imagesearch.search.Navigation
import com.nikialeksey.interview.imagesearch.search.Screen

class SimpleSearchScreen(
    private val images: Images,
    private val glide: RequestManager
) : Screen {

    override fun images(): Images {
        return images
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

    override fun glide(): RequestManager {
        return glide
    }
}