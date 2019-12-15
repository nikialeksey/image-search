package com.nikialeksey.interview.imagesearch.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikialeksey.interview.imagesearch.images.Image
import kotlinx.android.synthetic.main.search_item.view.*

class ImagesAdapter : PagedListAdapter<Image, ImagesAdapter.ViewHolder>(
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
        return ViewHolder(inflater.inflate(R.layout.search_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = getItem(position)
        if (image != null) {
            holder.bind(image)
        } else {
            holder.clear()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(image: Image) {
            Glide
                .with(itemView.context)
                .load(image.thumbnailUrl())
                .into(itemView.search_item_image)
        }

        fun clear() {
            itemView.search_item_image.setImageDrawable(null)
        }
    }
}