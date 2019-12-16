package com.nikialeksey.interview.imagesearch.images

interface Images {
    fun search(filter: String): ImagesState
}