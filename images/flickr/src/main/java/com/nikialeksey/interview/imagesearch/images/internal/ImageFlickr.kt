package com.nikialeksey.interview.imagesearch.images.internal

import com.nikialeksey.interview.imagesearch.images.Image

internal class ImageFlickr(
    val photo: PhotosResponse.Photo
) : Image {

    override fun thumbnailUrl(): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_z.jpg"
    }

    override fun largeUrl(): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_b.jpg"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageFlickr

        if (photo != other.photo) return false

        return true
    }

    override fun hashCode(): Int {
        return photo.hashCode()
    }


}