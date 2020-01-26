package com.nikialeksey.interview.imagesearch.search

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.Images
import com.nikialeksey.interview.imagesearch.images.ImagesState
import com.nikialeksey.interview.imagesearch.images.ProgressState


class ScreenViewModel(
    private val images: Images
) : ViewModel() {

    val imagesState: LiveData<ImagesState>
    val imagesResult: LiveData<PagedList<Image>>
    val imagesProgress: LiveData<ProgressState>

    val filter: MutableLiveData<String> = MutableLiveData()
    val searchMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val onSearchFocusChangeListener: View.OnFocusChangeListener =
        View.OnFocusChangeListener { _, focus ->
            if (focus) {
                searchMode.value = focus
            }
        }

    init {
        imagesState = Transformations.map(filter) {
            images.search(it)
        }
        imagesResult = Transformations.switchMap(imagesState) {
            it.images()
        }
        imagesProgress = Transformations.switchMap(imagesState) {
            it.progress()
        }
        filter.value = ""
    }

    fun onCloseSearchMode() {
        searchMode.value = false
        filter.value = ""
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
        fun bindFocus(view: EditText, focus: MutableLiveData<Boolean>) {
            val focusValue = focus.value ?: false
            if (focusValue && !view.hasFocus()) {
                view.requestFocus()
            } else if (!focusValue && view.hasFocus()) {
                view.clearFocus()

                val imm =
                    view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}