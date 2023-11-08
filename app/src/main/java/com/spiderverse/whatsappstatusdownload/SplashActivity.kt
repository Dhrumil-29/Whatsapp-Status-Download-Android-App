package com.spiderverse.whatsappstatusdownload

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.storage.StorageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.spiderverse.whatsappstatusdownload.databinding.ActivitySplashBinding
import com.spiderverse.whatsappstatusdownload.utils.FileUtils
import com.spiderverse.whatsappstatusdownload.utils.Utils.Companion.REQUEST_CODE
import java.util.Objects

class SplashActivity : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    private val handler: Handler = Handler(Looper.getMainLooper())

    //Permission that we need
    private val PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!checkPermissionDenied()) {
            goToMainScreen()
        } else {
            // ask for give permission

            // >= Android 6.0 (Marshmallow) comes runtime requestPermission to the user

            // >= Android 11.0 request persistence permission for fetching whatsapp status
            requestPermissionForAboveAndroid10()
        }

    }

    val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val data: Intent = result.data!!
                applicationContext.contentResolver.takePersistableUriPermission(
                    data.data!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )

                requestPermissionForAppFolder()
//                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
//                goToMainScreen()
            }
        }
    }

    val activityResultLauncherForAppFolder: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val data: Intent = result.data!!
                applicationContext.contentResolver.takePersistableUriPermission(
                    data.data!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )

                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                goToMainScreen()
            }
        }
    }

    private fun requestPermissionForAboveAndroid10() {
        val sm: StorageManager =
            applicationContext.getSystemService(Context.STORAGE_SERVICE) as StorageManager

        val intent: Intent = sm.primaryStorageVolume.createOpenDocumentTreeIntent()
        val startDir: String = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
        var uri: Uri? = intent.getParcelableExtra("android.provider.extra.INITIAL_URI")

//        This line retrieves the initial URI from the intent, which is typically the root directory of the storage volume.
        var scheme: String = uri.toString()
        scheme = scheme.replace("/root/", "/document/")
        scheme += "%3A$startDir"

        uri = Uri.parse(scheme)

        intent.putExtra("android.provider.extra.INITIAL_URI", uri)
        intent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION

        activityResultLauncher.launch(intent)
    }

    private fun requestPermissionForAppFolder() {
        if(FileUtils.createAppFolder() == null) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            return
        }

        val sm: StorageManager =
            applicationContext.getSystemService(Context.STORAGE_SERVICE) as StorageManager

        val intent: Intent = sm.primaryStorageVolume.createOpenDocumentTreeIntent()
        val startDir: String = "Download%2FWhatsAppStatusSaver"
        var uri: Uri? = intent.getParcelableExtra("android.provider.extra.INITIAL_URI")

//        This line retrieves the initial URI from the intent, which is typically the root directory of the storage volume.
        var scheme: String = uri.toString()
        scheme = scheme.replace("/root/", "/document/")
        scheme += "%3A$startDir"

        uri = Uri.parse(scheme)

        intent.putExtra("android.provider.extra.INITIAL_URI", uri)
        intent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION

        activityResultLauncherForAppFolder.launch(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty()) {
            if (checkPermissionDenied()) {
                // Clear Data of Application, So that it can request for permissions again
                (Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE)) as ActivityManager).clearApplicationUserData()
                recreate()
            } else {
                goToMainScreen()
            }
        }
    }


    private fun goToMainScreen() {
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    private fun checkPermissionDenied(): Boolean {
        return contentResolver.persistedUriPermissions.isEmpty()
    }

}