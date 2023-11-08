package com.spiderverse.whatsappstatusdownload.model

import android.os.Parcel
import android.os.Parcelable
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.Serializable

class StatusModel() : Serializable{
    constructor(documentFile: DocumentFile) : this() {
        this.documentFile = documentFile
        this.isVideo = documentFile.name?.endsWith(MP4) ?: false
        this.isNoMediaFile = documentFile.name?.endsWith(".nomedia") ?: false
    }

    var documentFile: DocumentFile? = null
    var isVideo: Boolean = false
    val MP4: String = ".mp4"
    var isNoMediaFile = false
}