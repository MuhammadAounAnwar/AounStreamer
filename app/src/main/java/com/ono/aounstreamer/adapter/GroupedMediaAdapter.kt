package com.ono.aounstreamer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.ono.aounstreamer.R
import com.ono.streamerlibrary.domain.model.MediaItem

class GroupedMediaAdapter(
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<GroupedMediaAdapter.GroupedMediaViewHolder>() {

    private val groupedMedia = mutableMapOf<String, PagingData<MediaItem>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupedMediaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.outer_item, parent, false)
        return GroupedMediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupedMediaViewHolder, position: Int) {
        val mediaType = groupedMedia.keys.toList()[position]
        val mediaPagingData = groupedMedia[mediaType]
        holder.bind(mediaType, mediaPagingData, onItemSelected)
    }

    override fun getItemCount() = groupedMedia.size

    fun submitData(newGroupedMedia: Map<String, PagingData<MediaItem>>) {
        groupedMedia.clear()
        groupedMedia.putAll(newGroupedMedia)
        notifyDataSetChanged()
    }

    inner class GroupedMediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mediaTypeHeader: TextView = itemView.findViewById(R.id.mediaTypeHeader)
        private val mediaItemsRecyclerView: RecyclerView = itemView.findViewById(R.id.mediaItemsRecyclerView)

        fun bind(mediaType: String, mediaPagingData: PagingData<MediaItem>?, onItemSelected: (String) -> Unit) {
            mediaTypeHeader.text = mediaType

            val mediaAdapter = MediaItemsAdapter(onItemSelected)
            mediaItemsRecyclerView.adapter = mediaAdapter
//            mediaPagingData?.let { mediaAdapter.submitData(it) }
        }
    }
}
