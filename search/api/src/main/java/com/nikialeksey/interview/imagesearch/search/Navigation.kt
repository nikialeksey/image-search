package com.nikialeksey.interview.imagesearch.search

import androidx.navigation.NavController
import com.nikialeksey.interview.imagesearch.images.Image

interface Navigation {
    fun openImage(navController: NavController, image: Image)
}