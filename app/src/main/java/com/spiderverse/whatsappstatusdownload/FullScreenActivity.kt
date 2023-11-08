package com.spiderverse.whatsappstatusdownload

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.documentfile.provider.DocumentFile
import com.spiderverse.whatsappstatusdownload.databinding.ActivityFullScreenBinding

class FullScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullScreenBinding
//    private lateinit var imageData : StatusModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val documentUriString = intent.getStringExtra("documentUri")
        val mimeType = intent.getStringExtra("mimeType")

        val statusUri = Uri.parse(documentUriString)

        val statusDocument = DocumentFile.fromSingleUri(this, statusUri)

        if (statusDocument!!.name!!.endsWith(".mp4", ignoreCase = true)) {
            // Video
            binding.videoStatusView.visibility = View.VISIBLE
            binding.touchImageView.visibility = View.GONE

            val mediaController = MediaController(this)
            binding.videoStatusView.setVideoURI(statusUri)
            binding.videoStatusView.requestFocus()
            binding.videoStatusView.setMediaController(mediaController)
            mediaController.setAnchorView(binding.videoStatusView)
            binding.videoStatusView.start()

        } else {
            // Image
            binding.videoStatusView.visibility = View.GONE
            binding.touchImageView.visibility = View.VISIBLE
            binding.touchImageView.setImageURI(statusUri)
        }
    }

}