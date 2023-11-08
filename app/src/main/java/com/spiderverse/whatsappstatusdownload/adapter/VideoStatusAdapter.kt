package com.spiderverse.whatsappstatusdownload.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spiderverse.whatsappstatusdownload.FullScreenActivity
import com.spiderverse.whatsappstatusdownload.R
import com.spiderverse.whatsappstatusdownload.databinding.RowImageVideoStatusBinding
import com.spiderverse.whatsappstatusdownload.model.StatusModel
import com.spiderverse.whatsappstatusdownload.utils.FileUtils

class VideoStatusAdapter(private val context: Context, val list: List<StatusModel>) :
    RecyclerView.Adapter<VideoStatusAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(binding: RowImageVideoStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val statusPreviewImage: ImageView = binding.statusPreviewImage
        val downloadVideo: Button = binding.btnDownload
        val videoPlayButton : ImageView = binding.ivVideoPlay
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): VideoStatusAdapter.VideoViewHolder {
        val binding = RowImageVideoStatusBinding.inflate(LayoutInflater.from(context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoStatusAdapter.VideoViewHolder, position: Int) {
        val videoData = list[position]

        holder.videoPlayButton.visibility = View.VISIBLE

        Glide.with(context).load(videoData.documentFile!!.uri)
            .placeholder(R.drawable.image_placeholder).into(holder.statusPreviewImage)

        holder.statusPreviewImage.setOnClickListener {
            val intent = Intent(context, FullScreenActivity::class.java)
            //we can pass information like the DocumentFile's URI and MIME type in the intent and re-create the DocumentFile when needed.
            intent.putExtra("documentUri", videoData.documentFile!!.uri.toString())
            intent.putExtra("mimeType", videoData.documentFile!!.type)
            context.startActivity(intent)
        }

        holder.downloadVideo.setOnClickListener {
            if (FileUtils.copyVideoToSaverFolder(
                    context, contentResolver = context.contentResolver, videoData.documentFile!!.uri
                )
            ) Toast.makeText(context, "File Saved...", Toast.LENGTH_SHORT).show()
            else Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size;
    }
}