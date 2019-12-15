package com.nikialeksey.interview.imagesearch.images.internal

internal data class PhotosResponse(
    val photos: Photos?,
    val stat: String,
    val code: Int,
    val message: String?
) {
    internal data class Photos(
        val page: Int,
        val pages: Int,
        val perpage: Int,
        val total: Int,
        val photo: List<Photo>
    )

    internal data class Photo(
        val id: String,
        val owner: String,
        val secret: String,
        val server: String,
        val farm: Int,
        val title: String,
        val ispublic: Int,
        val isfrient: Int,
        val isfamily: Int
    )
}