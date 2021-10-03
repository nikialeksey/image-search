package com.nikialeksey.interview.imagesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat

class Activity : AppCompatActivity(R.layout.activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}