package com.nikialeksey.interview.imagesearch.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikialeksey.interview.imagesearch.images.Image
import com.nikialeksey.interview.imagesearch.search.impl.R
import kotlinx.android.synthetic.main.item_search.view.*

class ImagesAdapter(
    private val imageClickListener: (image: Image) -> Unit
) : PagedListAdapter<Image, ImagesAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldImage: Image, newImage: Image): Boolean {
            return oldImage == newImage
        }

        override fun areContentsTheSame(
            oldImage: Image,
            newImage: Image
        ): Boolean {
            return oldImage.equals(newImage)
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.item_search, parent, false),
            ViewImageClickListener(imageClickListener)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = getItem(position)
        if (image != null) {
            holder.bind(image)
        } else {
            holder.clear()
        }
    }

    inner class ViewHolder(
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

    inner class ViewImageClickListener(
        private val imageClickListener: (image: Image) -> Unit
    ) : View.OnClickListener {
        var image: Image? = null

        override fun onClick(view: View?) {
            image?.let { imageClickListener(it) }
        }
    }
}