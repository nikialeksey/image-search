package com.nikialeksey.interview.imagesearch.show

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide

class ScreenViewModel(
    private val navController: NavController
) : ViewModel() {
    var url: String = ""
    var thumbnailUrl: String = ""

    fun onBackClick() {
        navController.navigateUp()
    }

    companion object {
        @BindingAdapter("url")
        @JvmStatic
        fun bindUrl(imageView: ImageView, url: String) {
            Glide.with(imageView)
                .load(url)
                .thumbnail()
                .into(imageView)
        }
    }
}