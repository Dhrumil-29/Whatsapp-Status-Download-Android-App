package com.spiderverse.whatsappstatusdownload.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spiderverse.whatsappstatusdownload.databinding.RowImageStatusBinding
import com.squareup.picasso.Picasso

class ImageStatusAdapter(private val context: Context,val list:List<String>): RecyclerView.Adapter<ImageStatusAdapter.ImageViewHolder>(){

    inner class ImageViewHolder(val binding: RowImageStatusBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageStatusAdapter.ImageViewHolder {
        val binding = RowImageStatusBinding.inflate(LayoutInflater.from(context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageStatusAdapter.ImageViewHolder, position: Int) {
        Picasso.get().load(list[position]).into(holder.binding.imageStatus)
    }

    override fun getItemCount(): Int {
        return list.size;
    }
}