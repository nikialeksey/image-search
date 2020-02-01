package com.nikialeksey.interview.imagesearch

import androidx.navigation.NavController
import com.bumptech.glide.RequestManager
import com.nikialeksey.interview.imagesearch.show.Navigation
import com.nikialeksey.interview.imagesearch.show.Screen

class SimpleShowScreen(
    private val glide: RequestManager
) : Screen {
    override fun navigation(): Navigation {
        return object: Navigation {
            override fun back(navController: NavController) {
                navController.navigateUp()
            }
        }
    }

    override fun glide(): RequestManager {
        return glide
    }
}