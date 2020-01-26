package com.nikialeksey.interview.imagesearch.images.internal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.PhotosResponse
import com.nikialeksey.interview.imagesearch.images.ProgressState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class ImagesDataSource(
    private val initialPage: Int,
    private val photosResponse: (page: Int) -> Call<PhotosResponse>,
    private val progressState: MutableLiveData<ProgressState>,
    private var retry: () -> Unit = {}
) : PageKeyedDataSource<Int, Image>() {

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        // ignored
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Image>
    ) {
        retry = { loadInitial(params, callback) }

        progressState.postValue(ProgressState.inProgress())
        photosResponse(initialPage)
            .enqueue(object : Callback<PhotosResponse> {
                override fun onResponse(
                    call: Call<PhotosResponse>,
                    response: Response<PhotosResponse>
                ) {
                    val photosResponse: PhotosResponse? = response.body()
                    val photos: PhotosResponse.Photos? = photosResponse?.photos
                    if (photos != null) {
                        retry = {}
                        progressState.postValue(ProgressState.success())
                        callback.onResult(photos.photo.map(::ImageFlickr), null, initialPage + 1)
                    } else if (photosResponse?.message != null) {
                        val message = photosResponse.message
                        progressState.postValue(ProgressState.failed(message))
                    } else {
                        progressState.postValue(ProgressState.failed("Invalid response: ${response.raw().body()?.string()}"))
                    }
                }

                override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                    Log.d(TAG, "Request photos error", t)
                    progressState.postValue(ProgressState.failed("An exception occurred: ${t.message}"))
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        retry = { loadAfter(params, callback) }

        progressState.postValue(ProgressState.inProgress())
        photosResponse(params.key)
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
                        retry = {}
                        progressState.postValue(ProgressState.success())
                        callback.onResult(photos.photo.map(::ImageFlickr), key)
                    } else if (photosResponse?.message != null) {
                        val message = photosResponse.message
                        progressState.postValue(ProgressState.failed(message))
                    } else {
                        progressState.postValue(ProgressState.failed("Invalid response: ${response.raw().body()?.string()}"))
                    }
                }

                override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                    Log.d(TAG, "Request photos error", t)
                    progressState.postValue(ProgressState.failed("An exception occurred: ${t.message}"))
                }
            })
    }

    fun retry() {
        val savedRetry = this.retry
        this.retry = {}
        savedRetry()
    }

    companion object {
        const val TAG = "ImagesDataSource"
    }
}