package com.nikialeksey.interview.imagesearch.show

import com.bumptech.glide.RequestManager

interface Screen {
    fun navigation(): Navigation
    fun glide(): RequestManager
}