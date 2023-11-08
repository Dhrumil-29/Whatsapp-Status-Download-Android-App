package com.spiderverse.whatsappstatusdownload.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spiderverse.whatsappstatusdownload.FullScreenActivity
import com.spiderverse.whatsappstatusdownload.R
import com.spiderverse.whatsappstatusdownload.databinding.RowImageVideoStatusBinding
import com.spiderverse.whatsappstatusdownload.model.StatusModel

class SavedStatusAdapter(private val context: Context, val list: List<StatusModel>) :
    RecyclerView.Adapter<SavedStatusAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(binding: RowImageVideoStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val statusPreview: ImageView = binding.statusPreviewImage
        val downloadImage: Button = binding.btnDownload
        val constraint : ConstraintLayout = binding.constraint
        val videoPlayButton : ImageView = binding.ivVideoPlay
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SavedStatusAdapter.ImageViewHolder {
        val binding = RowImageVideoStatusBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedStatusAdapter.ImageViewHolder, position: Int) {
        val savedStatusData = list[position]

        if(savedStatusData.isVideo)
            holder.videoPlayButton.visibility = View.VISIBLE
        else
            holder.videoPlayButton.visibility = View.GONE

        val constraintSet = ConstraintSet()
        constraintSet.clone(holder.constraint)
        constraintSet.constrainMaxHeight(R.id.status_preview_image,context.resources.getDimensionPixelSize(R.dimen.spacing_200dp))
        constraintSet.constrainMinHeight(R.id.status_preview_image,context.resources.getDimensionPixelSize(R.dimen.spacing_200dp))
        constraintSet.applyTo(holder.constraint)

        Glide.with(context).load(savedStatusData.documentFile!!.uri)
            .placeholder(R.drawable.image_placeholder).into(holder.statusPreview)

        holder.statusPreview.setOnClickListener {
            val intent = Intent(context, FullScreenActivity::class.java)
            //we can pass information like the DocumentFile's URI and MIME type in the intent and re-create the DocumentFile when needed.
            intent.putExtra("documentUri", savedStatusData.documentFile!!.uri.toString())
            intent.putExtra("mimeType", savedStatusData.documentFile!!.type)
            context.startActivity(intent)
        }

        holder.downloadImage.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return list.size;
    }
}