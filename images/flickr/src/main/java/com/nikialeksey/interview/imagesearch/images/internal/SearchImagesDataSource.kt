package com.nikialeksey.interview.imagesearch.images.internal

import androidx.paging.PageKeyedDataSource
import com.nikialeksey.interview.imagesearch.images.Image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class SearchImagesDataSource(
    val api: FlickrApi,
    val apiKey: String,
    val filter: String,
    val pageSize: Int,
    val initialPage: Int
) : PageKeyedDataSource<Int, Image>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Image>
    ) {
        api.search(filter, initialPage, pageSize, apiKey)
            .enqueue(object : Callback<PhotosResponse> {
                override fun onResponse(
                    call: Call<PhotosResponse>,
                    response: Response<PhotosResponse>
                ) {
                    val photosResponse: PhotosResponse? = response.body()
                    val photos: PhotosResponse.Photos? = photosResponse?.photos
                    if (photos != null) {
                        callback.onResult(photos.photo.map(::ImageFlickr), null, initialPage + 1)
                    }
                }

                override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                    throw t
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.search(filter, params.key, pageSize, apiKey)
            .enqueue(object : Callback<PhotosResponse> {
                override fun onResponse(
                    call: Call<PhotosResponse>,
                    response: Response<PhotosResponse>
                ) {
                    val photosResponse: PhotosResponse? = response.body()
                    val photos: PhotosResponse.Photos? = photosResponse?.photos
                    val adjacentKey = if (params.key > 1) {
                        params.key - 1
                    } else {
                        null
                    }
                    if (photos != null) {
                        callback.onResult(photos.photo.map(::ImageFlickr), adjacentKey)
                    }
                }

                override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                    throw t
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.search(filter, params.key, pageSize, apiKey)
            .enqueue(object : Callback<PhotosResponse> {
                override fun onResponse(
                    call: Call<PhotosResponse>,
                    response: Response<PhotosResponse>
                ) {
                    val photosResponse: PhotosResponse? = response.body()
                    val photos: PhotosResponse.Photos? = photosResponse?.photos
                    if (photos != null) {
                        val key = if (photos.page * photos.perpage < photos.total) {
                            params.key + 1
                        } else {
                            null
                        }

                        callback.onResult(photos.photo.map(::ImageFlickr), key)
                    }
                }

                override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                    throw t
                }
            })
    }
}