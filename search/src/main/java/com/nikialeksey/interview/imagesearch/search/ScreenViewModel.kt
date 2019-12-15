package com.nikialeksey.interview.imagesearch.search

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.Images


class ScreenViewModel(
    private val images: Images
) : ViewModel() {

    val imagesLive: LiveData<PagedList<Image>>

    val filter: MutableLiveData<String> = MutableLiveData()
    val searchMode: ObservableBoolean = ObservableBoolean(false)
    val onSearchFocusChangeListener: View.OnFocusChangeListener =
        View.OnFocusChangeListener { _, focus ->
            if (focus) {
                searchMode.set(focus)
            }
        }

    init {
        imagesLive = Transformations.switchMap(filter) {
            if (searchMode.get()) {
                searchImages()
            } else {
                recentImages()
            }
        }
        filter.value = ""
    }

    fun onCloseSearchMode() {
        searchMode.set(false)
        filter.value = ""
    }

    private fun searchImages(): LiveData<PagedList<Image>> {
        return LivePagedListBuilder(
            images.search(filter.value ?: ""),
            images.pageSize()
        ).build()
    }

    private fun recentImages(): LiveData<PagedList<Image>> {
        return LivePagedListBuilder(
            images.resent(),
            images.pageSize()
        ).build()
    }

    companion object {
        @BindingAdapter("onFocus")
        @JvmStatic
        fun bindFocusChange(view: View, onFocusChangeListener: View.OnFocusChangeListener) {
            if (view.onFocusChangeListener == null) {
                view.onFocusChangeListener = onFocusChangeListener
            }
        }

        @BindingAdapter("focus")
        @JvmStatic
        fun bindFocus(view: EditText, focus: ObservableBoolean) {
            if (focus.get() && !view.hasFocus()) {
                view.requestFocus()
            } else if (!focus.get() && view.hasFocus()) {
                view.clearFocus()

                val imm =
                    view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}