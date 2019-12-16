package com.nikialeksey.interview.imagesearch.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.ProgressState
import com.nikialeksey.interview.imagesearch.search.impl.R
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_search.view.*
import java.lang.IllegalArgumentException

class ImagesAdapter(
    private val imageClickListener: (image: Image) -> Unit,
    private val retryListener: () -> Unit
) : PagedListAdapter<Image, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldImage: Image, newImage: Image): Boolean {
            return oldImage == newImage
        }

        override fun areContentsTheSame(oldImage: Image, newImage: Image): Boolean {
            return oldImage.equals(newImage)
        }
    }
) {
    private var loadingState: ProgressState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == R.layout.item_search) {
            ImageViewHolder(
                inflater.inflate(R.layout.item_search, parent, false),
                ViewImageClickListener(imageClickListener)
            )
        } else if (viewType == R.layout.item_loading) {
            LoadingViewHolder(
                inflater.inflate(R.layout.item_loading, parent, false),
                retryListener
            )
        } else {
            throw IllegalArgumentException("Unexpected view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == R.layout.item_search) {
            val image = getItem(position)
            if (image != null) {
                (holder as ImageViewHolder).bind(image)
            } else {
                (holder as ImageViewHolder).clear()
            }
        } else if (getItemViewType(position) == R.layout.item_loading) {
            loadingState?.let {
                (holder as LoadingViewHolder).bind(it)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && hasLoading()) {
            R.layout.item_loading
        } else {
            R.layout.item_search
        }
    }

    override fun getItemCount(): Int {
        val originalCount = super.getItemCount()
        return if (hasLoading()) {
            originalCount + 1
        } else {
            originalCount
        }
    }

    fun updateLoadingState(loadingState: ProgressState) {
        val previousState = this.loadingState
        val hadExtraRow = hasLoading()
        this.loadingState = loadingState
        val hasExtraRow = hasLoading()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != loadingState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasLoading(): Boolean {
        return loadingState != null && loadingState?.isSuccess() != true
    }

    inner class ImageViewHolder(
        itemView: View,
        private val imageClickListener: ViewImageClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(image: Image) {
            imageClickListener.image = image
            itemView.setOnClickListener(imageClickListener)
            Glide
                .with(itemView.context)
                .load(image.thumbnailUrl())
                .into(itemView.search_item_image)
        }

        fun clear() {
            itemView.search_item_image.setImageDrawable(null)
        }
    }

    inner class LoadingViewHolder(
        itemView: View,
        private val onRetry: () -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.loading_retry.setOnClickListener { onRetry() }
        }

        fun bind(loadingState: ProgressState) {
            itemView.loading_progress.visibility = if (loadingState.isInProgress()) View.VISIBLE else View.GONE
            itemView.loading_message.visibility = if (loadingState.isFailed()) View.VISIBLE else View.GONE
            itemView.loading_retry.visibility = if (loadingState.isFailed()) View.VISIBLE else View.GONE

            itemView.loading_message.text = loadingState.message
        }
    }

    inner class ViewImageClickListener(
        private val imageClickListener: (image: Image) -> Unit
    ) : View.OnClickListener {
        var image: Image? = null

        override fun onClick(view: View?) {
            image?.let { imageClickListener(it) }
        }
    }
}