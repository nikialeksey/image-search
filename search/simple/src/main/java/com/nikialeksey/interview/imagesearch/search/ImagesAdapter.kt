package com.nikialeksey.interview.imagesearch.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.images.ProgressState
import com.nikialeksey.interview.imagesearch.search.impl.R
import com.nikialeksey.interview.imagesearch.search.impl.databinding.ItemLoadingBinding
import com.nikialeksey.interview.imagesearch.search.impl.databinding.ItemSearchBinding
import java.lang.IllegalArgumentException

class ImagesAdapter(
    private val glide: RequestManager,
    private val imageClickListener: (image: Image, shared: View) -> Unit,
    private val retryListener: () -> Unit
) : PagedListAdapter<Image, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldImage: Image, newImage: Image): Boolean {
            return oldImage.thumbnailUrl() == newImage.thumbnailUrl()
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
                ItemSearchBinding.inflate(inflater, parent, false),
                ViewImageClickListener(imageClickListener)
            )
        } else if (viewType == R.layout.item_loading) {
            LoadingViewHolder(
                ItemLoadingBinding.inflate(inflater, parent, false),
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
        private val binding: ItemSearchBinding,
        private val imageClickListener: ViewImageClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            imageClickListener.image = image
            itemView.setOnClickListener(imageClickListener)
            glide
                .load(image.thumbnailUrl())
                .into(binding.searchItemImage)
            binding.searchItemImage.transitionName = image.thumbnailUrl()
        }

        fun clear() {
            binding.searchItemImage.setImageDrawable(null)
        }
    }

    inner class LoadingViewHolder(
        private val binding: ItemLoadingBinding,
        private val onRetry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.loadingRetry.setOnClickListener { onRetry() }
        }

        fun bind(loadingState: ProgressState) {
            binding.loadingProgress.isVisible = loadingState.isInProgress()
            binding.loadingMessage.isVisible = loadingState.isFailed()
            binding.loadingRetry.isVisible = loadingState.isFailed()

            binding.loadingMessage.text = loadingState.message
        }
    }

    inner class ViewImageClickListener(
        private val imageClickListener: (image: Image, shared: View) -> Unit
    ) : View.OnClickListener {
        var image: Image? = null

        override fun onClick(view: View) {
            image?.let { imageClickListener(it, view) }
        }
    }
}