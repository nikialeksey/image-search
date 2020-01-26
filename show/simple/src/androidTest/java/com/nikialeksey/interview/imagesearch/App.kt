package com.nikialeksey.interview.imagesearch

import android.app.Application
import androidx.navigation.NavController
import com.nikialeksey.interview.imagesearch.show.Navigation
import com.nikialeksey.interview.imagesearch.show.Screen

class App : Application(), com.nikialeksey.interview.imagesearch.show.App {

    private lateinit var showScreen: Screen

    override fun onCreate() {
        super.onCreate()

        showScreen = object : Screen {
            override fun navigation(): Navigation {
                return object : Navigation {
                    override fun back(navController: NavController) {
                        // nothing
                    }
                }
            }
        }
    }

    override fun showScreen(): Screen {
        return showScreen
    }
}