package com.ono.aounstreamer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ono.aounstreamer.R
import com.ono.streamerlibrary.domain.model.MediaItem

class MediaItemsAdapter(
    private val onItemSelected: (String) -> Unit
) : PagingDataAdapter<MediaItem, MediaItemsAdapter.MediaItemViewHolder>(MediaItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inner_item, parent, false)
        return MediaItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, onItemSelected) }
    }

    inner class MediaItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)
        private val mediaTitle: TextView = itemView.findViewById(R.id.mediaTitle)

        fun bind(item: MediaItem, onItemSelected: (String) -> Unit) {
            // Load image, e.g., with Glide or Coil
            // Glide.with(mediaImage.context).load(item.imageUrl).into(mediaImage)
            mediaTitle.text = item.title

            itemView.setOnClickListener {
                onItemSelected(item.id.toString())
            }
        }
    }
}

class MediaItemDiffCallback : DiffUtil.ItemCallback<MediaItem>() {
    override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem) = oldItem == newItem
}
