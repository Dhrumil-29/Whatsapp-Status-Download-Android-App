package com.spiderverse.whatsappstatusdownload.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.spiderverse.whatsappstatusdownload.FullScreenActivity
import com.spiderverse.whatsappstatusdownload.R
import com.spiderverse.whatsappstatusdownload.databinding.RowImageStatusBinding
import com.spiderverse.whatsappstatusdownload.model.StatusModel
import com.spiderverse.whatsappstatusdownload.utils.FileUtils
import com.spiderverse.whatsappstatusdownload.utils.Utils
import com.squareup.picasso.Picasso
import java.io.File
import java.io.Serializable

class ImageStatusAdapter(private val context: Context, val list: List<StatusModel>) :
    RecyclerView.Adapter<ImageStatusAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: RowImageStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val statusImage: ImageView = binding.imageStatus
        val downloadImage: Button = binding.btnDownload
    }

//    private val gson = Gson()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ImageStatusAdapter.ImageViewHolder {
        val binding = RowImageStatusBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageStatusAdapter.ImageViewHolder, position: Int) {
        val imageData = list[position]
        if (imageData.isAboveAndroid11) {
            Glide.with(context).load(imageData.documentFile!!.uri)
                .placeholder(R.drawable.image_placeholder).into(holder.statusImage)
        } else {
            Glide.with(context).load(imageData.file).placeholder(R.drawable.image_placeholder)
                .into(holder.statusImage)
        }

        holder.statusImage.setOnClickListener {
            val intent = Intent(context, FullScreenActivity::class.java)
            //we can pass information like the DocumentFile's URI and MIME type in the intent and re-create the DocumentFile when needed.
            intent.putExtra("documentUri",imageData.documentFile!!.uri.toString())
            intent.putExtra("mimeType",imageData.documentFile!!.type)
            context.startActivity(intent)
        }

        holder.downloadImage.setOnClickListener {
//            FileUtils.copyFile(imageData,context)
            if (FileUtils.copyImageToSaverFolder(
                    context, contentResolver = context.contentResolver, imageData.documentFile!!.uri
                )
            ) Toast.makeText(context, "File Saved...", Toast.LENGTH_SHORT).show()
            else Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size;
    }
}