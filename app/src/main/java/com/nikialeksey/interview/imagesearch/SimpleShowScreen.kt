package com.nikialeksey.interview.imagesearch

import androidx.navigation.NavController
import com.nikialeksey.interview.imagesearch.show.Navigation
import com.nikialeksey.interview.imagesearch.show.Screen

class SimpleShowScreen : Screen {
    override fun navigation(): Navigation {
        return object: Navigation {
            override fun back(navController: NavController) {
                navController.navigateUp()
            }
        }
    }
}