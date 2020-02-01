package com.nikialeksey.interview.imagesearch.search

import com.bumptech.glide.RequestManager
import com.nikialeksey.interview.imagesearch.images.Images

interface Screen {
    fun images(): Images
    fun navigation(): Navigation
    fun glide(): RequestManager
}