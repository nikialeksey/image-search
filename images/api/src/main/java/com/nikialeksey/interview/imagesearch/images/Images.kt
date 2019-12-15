package com.nikialeksey.interview.imagesearch.images

import androidx.paging.DataSource

interface Images {
    fun resent(): DataSource.Factory<Int, Image>
    fun search(filter: String): DataSource.Factory<Int, Image>
    fun pageSize(): Int
}