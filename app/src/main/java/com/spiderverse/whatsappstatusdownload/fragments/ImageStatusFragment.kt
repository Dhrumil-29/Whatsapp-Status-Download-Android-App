package com.spiderverse.whatsappstatusdownload.fragments

import android.app.Activity
import android.content.UriPermission
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.spiderverse.whatsappstatusdownload.adapter.ImageStatusAdapter
import com.spiderverse.whatsappstatusdownload.databinding.FragmentImageStatusBinding
import com.spiderverse.whatsappstatusdownload.model.StatusModel
import com.spiderverse.whatsappstatusdownload.utils.Utils
import java.util.Arrays
import java.util.concurrent.Executors

class ImageStatusFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentImageStatusBinding
    private lateinit var activity: Activity
    private var imageList: MutableList<StatusModel> = mutableListOf()
    private var imageStatusAdapter: ImageStatusAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentImageStatusBinding.inflate(layoutInflater, container, false)
        activity = requireActivity()

        initView()

        return fragmentBinding.root
    }

    private fun initView() {
        fetchImagesFromStatus();

        fragmentBinding.refreshStatusImage.setOnRefreshListener {
            fetchImagesFromStatus();
            fragmentBinding.refreshStatusImage.isRefreshing = false
        }
    }

    private fun fetchImagesFromStatus() {

//        check version and based on that fetch Images
//        above Android 11 : folder path will be Android/com.whatsapp/WhatsApp/Media/.Statuses
//        below Android 11 : "WhatsApp/Media/.Statuses"

        fetchFromAboveAndroid10()
    }

    private fun fetchFromBelowAndroid11() {
        Executors.newSingleThreadExecutor().execute {
            val mainHandler = Handler(Looper.getMainLooper())
            val statusFiles = Utils.WHATSAPP_STATUS_FOLDER_PATH.listFiles()
            Log.d(
                "Status File", "Length : ${Utils.WHATSAPP_STATUS_FOLDER_PATH.length()} " +
                        "can readable : ${Utils.WHATSAPP_STATUS_FOLDER_PATH.canRead()} " +
                        "Name : ${Utils.WHATSAPP_STATUS_FOLDER_PATH.name} "
            )

            imageList.clear()

            if (statusFiles != null && statusFiles.isNotEmpty()) {
                Arrays.sort(statusFiles)

                for (file in statusFiles) {

                    if (file.name.contains(".nomedia")) continue

                    val status = StatusModel(file, file.name, file.absolutePath)
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        imageList.add(status)
                    }
                }
                mainHandler.post {
                    if (imageList.size <= 0) {
                        fragmentBinding.tvMessage.visibility = View.VISIBLE
                    } else {
                        fragmentBinding.tvMessage.visibility = View.GONE
                    }
                    if (imageStatusAdapter == null) {
                        imageStatusAdapter = ImageStatusAdapter(requireContext(), imageList)
                        fragmentBinding.rvImage.adapter = imageStatusAdapter
                    }
                    imageStatusAdapter!!.notifyItemRangeChanged(0, imageList.size)
                }
            } else {
//                mainHandler.post {
                fragmentBinding.tvMessage.visibility = View.VISIBLE
            }
//            }
            fragmentBinding.refreshStatusImage.isRefreshing = false
        }
    }

    private fun fetchFromAboveAndroid10() {
        Executors.newSingleThreadExecutor().execute {
            val mainHandler: Handler = Handler(Looper.getMainLooper())

            val list: List<UriPermission> =
                requireActivity().contentResolver.persistedUriPermissions

            val file: DocumentFile? = DocumentFile.fromTreeUri(requireActivity(), list[0].uri)

            imageList.clear()

            if (file == null) {
//                mainHandler.post {
                fragmentBinding.tvMessage.visibility = View.VISIBLE
                fragmentBinding.refreshStatusImage.isRefreshing = false
//                }
                return@execute
            }

            val imageStatusFiles = file.listFiles()
            if (imageStatusFiles.isEmpty()) {
//                mainHandler.post {
                fragmentBinding.tvMessage.visibility = View.VISIBLE
                fragmentBinding.refreshStatusImage.isRefreshing = false
//                }
            } else {
                for (documentFile in imageStatusFiles) {
                    val imageStatus = StatusModel(documentFile)

                    if (!imageStatus.isVideo && !imageStatus.isNoMediaFile)
                        imageList.add(imageStatus)
                }
            }

            mainHandler.post {
                if (imageList.isEmpty()) {
                    fragmentBinding.tvMessage.visibility = View.VISIBLE
                } else {
                    if (imageStatusAdapter == null) {
                        imageStatusAdapter = ImageStatusAdapter(requireContext(), imageList)
                        fragmentBinding.rvImage.adapter = imageStatusAdapter
                    }
                    imageStatusAdapter!!.notifyItemRangeChanged(0, imageList.size)

                }
            }
            fragmentBinding.refreshStatusImage.isRefreshing = false
        }
    }
}