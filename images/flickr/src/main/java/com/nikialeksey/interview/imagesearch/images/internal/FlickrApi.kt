package com.nikialeksey.interview.imagesearch.images.internal

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface FlickrApi {
    @GET("services/rest/")
    fun search(
        @Query("text") filter: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("api_key") apiKey: String,
        @Query("method") method: String = "flickr.photos.search",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): Call<PhotosResponse>

    @GET("services/rest/")
    fun getRecent(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("api_key") apiKey: String,
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): Call<PhotosResponse>

    class Api
}