package com.spiderverse.whatsappstatusdownload.model

import android.os.Parcel
import android.os.Parcelable
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.Serializable

class StatusModel() : Serializable{
    constructor(documentFile: DocumentFile) : this() {
        this.documentFile = documentFile
        this.isAboveAndroid11 = true
        this.isVideo = documentFile.name?.endsWith(MP4) ?: false
        this.isNoMediaFile = documentFile.name?.endsWith(".nomedia") ?: false
    }

    constructor(file: File, title: String, path: String) : this() {
        this.file = file
        this.title = title
        this.path = path
        this.isAboveAndroid11 = true
        this.isVideo = file.name.endsWith(MP4)
    }

    var file: File? = null
    var documentFile: DocumentFile? = null
    var title: String = ""
    var path: String = ""
    var isAboveAndroid11: Boolean = false
    var isVideo: Boolean = false
    val MP4: String = ".mp4"
    var isNoMediaFile = false
}