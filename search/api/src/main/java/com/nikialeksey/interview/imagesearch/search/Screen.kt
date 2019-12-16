package com.nikialeksey.interview.imagesearch.search

import com.nikialeksey.interview.imagesearch.images.Images

interface Screen {
    fun images(): Images
    fun navigation(): Navigation
}