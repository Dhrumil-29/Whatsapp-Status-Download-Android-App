package com.spiderverse.whatsappstatusdownload.utils

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.storage.StorageManager
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.spiderverse.whatsappstatusdownload.model.StatusModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FileUtils {
    companion object {
        /*fun copyFile(status: StatusModel, context: Context) {
            val file: File = File(APP_DIR)

             check file exist or not
            val destinationFile = File(Utils.APP_FILE_PATH)
            if (!destinationFile.exists()) {
                if (!destinationFile.mkdirs()) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
//            Toast.makeText(context, "File created / existed", Toast.LENGTH_SHORT).show()

            var fileName: String

            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val currentDate = sdf.format(Date())

            if (status.isVideo) {
                fileName = "VID_${currentDate}.mp4"
            } else {
                fileName = "IMG_${currentDate}.jpg"
            }

//            val destFile = DocumentFile.fromFile(File(Utils.APP_FILE_PATH,fileName))

            try {
                                    val values = ContentValues()
                    val destinationUri: Uri

                    values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    values.put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_DCIM + "/status_saver"
                    )

                    val collectionUri: Uri
                    if (status.isVideo) {
                        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/*")
                        collectionUri =
                            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                    } else {
                        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/*")
                        collectionUri =
                            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                    }

                    destinationUri = context.contentResolver.insert(collectionUri, values)!!

                Toast.makeText(context, "copy pending", Toast.LENGTH_SHORT).show()

                val inputStream = context.contentResolver.openInputStream(status.documentFile!!.uri)

                val sm: StorageManager =
                    context.getSystemService(Context.STORAGE_SERVICE) as StorageManager

                val intent: Intent = sm.primaryStorageVolume.createOpenDocumentTreeIntent()
                var uri: Uri? = intent.getParcelableExtra("android.provider.extra.INITIAL_URI")

//        This line retrieves the initial URI from the intent, which is typically the root directory of the storage volume.
                var scheme: String = uri.toString()
                scheme = scheme.replace("/root/", "/document/")
                scheme += "${File.separator}Download/WhatsAppStatusSaver/${fileName}"

                uri = Uri.parse(scheme)

                val outputStream = context.contentResolver.openOutputStream(uri)

                val buffer = ByteArray(4096)
                var bytesRead: Int

                while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                    outputStream!!.write(buffer, 0, bytesRead)
                }

                inputStream.close()
                outputStream!!.close()

                Toast.makeText(context, "File saved...", Toast.LENGTH_SHORT).show()

                            val inputStream =
                                context.contentResolver.openInputStream(status.documentFile?.uri!!)
                            val outputStream =
                                context.contentResolver.openOutputStream(destinationUri)

                            IOUtils.copy(inputStream, outputStream)
            } catch (e: IOException) {
                Toast.makeText(context, "File Didn't saved", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

        }*/*/*/


        fun copyImageToSaverFolder(
            context: Context, contentResolver: ContentResolver, sourceImageUri: Uri
        ): Boolean {
            // Create a DocumentFile for the source image using the URI
            val sourceDocumentFile = DocumentFile.fromSingleUri(context, sourceImageUri)

            // Get the external storage directory DocumentFile
            val externalStorageDocumentFile =
                DocumentFile.fromFile(Environment.getExternalStorageDirectory())

            // Check if the "Download" folder exists, and create it if not
            val downloadFolder = getOrCreateFolder(externalStorageDocumentFile, "Download")

            // Check if the "WhatsAppStatusSaver" folder exists inside "Download," and create it if not
            val saverFolder = getOrCreateFolder(downloadFolder, "WhatsAppStatusSaver")
            if (sourceDocumentFile != null && saverFolder != null) {
                // Create a new DocumentFile in the "Saver" folder with the desired file name and MIME type
                val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                val currentDate = sdf.format(Date())

                val desiredFileName = "IMG_${currentDate}"
                val destinationFile = saverFolder.createFile("image/jpeg", desiredFileName)
                if (destinationFile != null) {
                    // Copy the content from the source DocumentFile to the destination DocumentFile
                    try {
                        val inputStream = contentResolver.openInputStream(sourceDocumentFile.uri)
                        val outputStream = contentResolver.openOutputStream(destinationFile.uri)
                        val buffer = ByteArray(4096)
                        var bytesRead: Int
                        while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                            outputStream!!.write(buffer, 0, bytesRead)
                        }
                        inputStream.close()
                        outputStream!!.close()
                        return true // Copying succeeded
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return false // Copying failed
        }

        fun getOrCreateFolder(parentFolder: DocumentFile?, folderName: String?): DocumentFile? {
            // Check if the folder exists, and create it if not
            var folder = parentFolder!!.findFile(folderName!!)
            if (folder == null) {
                folder = parentFolder.createDirectory(folderName)
            }
            return folder
        }
    }
}