package com.nikialeksey.interview.imagesearch.images

data class PhotosResponse(
    val photos: Photos?,
    val stat: String,
    val code: Int,
    val message: String?
) {
    data class Photos(
        val page: Int,
        val pages: Int,
        val perpage: Int,
        val total: Int,
        val photo: List<Photo>
    )

    data class Photo(
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