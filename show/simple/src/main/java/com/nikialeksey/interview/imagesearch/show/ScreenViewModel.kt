package com.nikialeksey.interview.imagesearch.show

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide

class ScreenViewModel : ViewModel() {
    var url: String = ""
    var thumbnailUrl: String = ""

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