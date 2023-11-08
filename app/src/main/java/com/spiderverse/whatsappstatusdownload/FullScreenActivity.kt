package com.spiderverse.whatsappstatusdownload

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.google.gson.Gson
import com.spiderverse.whatsappstatusdownload.databinding.ActivityFullScreenBinding
import com.spiderverse.whatsappstatusdownload.model.StatusModel

class FullScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFullScreenBinding
//    private lateinit var imageData : StatusModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val imageDataJson = intent.getStringExtra("DATA")
//
//        if(imageDataJson != null){
//            imageData = Gson().fromJson(imageDataJson,StatusModel::class.java)
//            imageData = intent.getStringExtra("DATA") as StatusModel
//        }

        val documentUriString = intent.getStringExtra("documentUri")
        val mimeType = intent.getStringExtra("mimeType")

        val imageUri = Uri.parse(documentUriString)

        binding.touchImageView.setImageURI(imageUri)
    }

}