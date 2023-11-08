package com.spiderverse.whatsappstatusdownload.fragments

import android.app.Activity
import android.content.UriPermission
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.spiderverse.whatsappstatusdownload.adapter.VideoStatusAdapter
import com.spiderverse.whatsappstatusdownload.databinding.FragmentVideoStatusBinding
import com.spiderverse.whatsappstatusdownload.model.StatusModel

class VideoStatusFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentVideoStatusBinding
    private lateinit var activity: Activity
    private var videoList: MutableList<StatusModel> = mutableListOf()
    private var videoStatusAdapter: VideoStatusAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentVideoStatusBinding.inflate(layoutInflater, container, false)
        activity = requireActivity()

        initView()

        return fragmentBinding.root
    }


    private fun initView() {
        fetchVideosFromStatus()

        fragmentBinding.refreshVideoStatus.setOnRefreshListener {
            fetchVideosFromStatus()
            fragmentBinding.refreshVideoStatus.isRefreshing = false
        }
    }

    private fun fetchVideosFromStatus() {
//        Executors.newSingleThreadExecutor().execute {
//            val mainHandler = Handler(Looper.getMainLooper())

            val list: List<UriPermission> =
                requireActivity().contentResolver.persistedUriPermissions

            val file: DocumentFile? = DocumentFile.fromTreeUri(requireActivity(), list[1].uri)

            videoList.clear()

            if (file == null) {
//                mainHandler.post {
                fragmentBinding.tvMessage.visibility = View.VISIBLE
                fragmentBinding.refreshVideoStatus.isRefreshing = false
//                }
                return
            }

            val videoStatusFiles = file.listFiles()
            if (videoStatusFiles.isEmpty()) {
//                mainHandler.post {
                fragmentBinding.tvMessage.visibility = View.VISIBLE
                fragmentBinding.refreshVideoStatus.isRefreshing = false
//                }
            } else {
                for (documentFile in videoStatusFiles) {
                    val videoStatus = StatusModel(documentFile)

                    if (videoStatus.isVideo && !videoStatus.isNoMediaFile)
                        videoList.add(videoStatus)
                }
            }

//            mainHandler.post {
                if (videoList.isEmpty()) {
                    fragmentBinding.tvMessage.visibility = View.VISIBLE
                } else {
                    if (videoStatusAdapter == null) {
                        videoStatusAdapter = VideoStatusAdapter(requireContext(), videoList)
                        fragmentBinding.rvVideoStatus.adapter = videoStatusAdapter
                    }
                    videoStatusAdapter!!.notifyItemRangeChanged(0, videoList.size)

                }
//            }
            fragmentBinding.refreshVideoStatus.isRefreshing = false
        }
//    }
}