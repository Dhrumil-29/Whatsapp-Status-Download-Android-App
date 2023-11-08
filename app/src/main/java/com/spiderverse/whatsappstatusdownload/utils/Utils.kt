package com.spiderverse.whatsappstatusdownload.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.spiderverse.whatsappstatusdownload.model.StatusModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {
    companion object {
        val REQUEST_CODE: Int = 1234
        var APP_DIR: String? = null
        val APP_FILE_PATH: String = "${Environment.getExternalStorageDirectory().path}${File.separator}/Download/WhatsAppStatusSaver"
        val RELATIVE_APP_FILE_PATH: String = "/Download/WhatsAppStatusSaver"

        /*fun createFileFolder(): Boolean {
            Log.d("TEST : ", "Folder Check")
            if (!APP_FILE_PATH.exists())
            // return folder created.
                return APP_FILE_PATH.mkdirs()
            // if folder already is existed then return true
            Log.d("TEST : ", "Folder Checked")
            return true
        }*/

//        val WHATSAPP_STATUS_FOLDER_PATH: File = File(
//            Environment.getExternalStorageDirectory(),
//            "${File.separator}WhatsApp/Media/.Statuses"
//        )
        val WHATSAPP_STATUS_FOLDER_PATH: File = File(
            Environment.getExternalStorageDirectory(),
            "${File.separator}Download"
        )

        /*fun testFolder(): Boolean {
            Log.d("TEST : ", "Folder Check")
            if (!WHATSAPP_STATUS_FOLDER_PATH.exists())
            // return folder created.
                return WHATSAPP_STATUS_FOLDER_PATH.mkdirs()
            // if folder already is existed then return true
            Log.d("TEST : ", "Folder Exisited")
            return true
        }*/
    }
}