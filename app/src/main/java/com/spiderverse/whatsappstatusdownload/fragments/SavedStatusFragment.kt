package com.spiderverse.whatsappstatusdownload.fragments

import android.content.UriPermission
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.spiderverse.whatsappstatusdownload.adapter.ImageStatusAdapter
import com.spiderverse.whatsappstatusdownload.adapter.SavedStatusAdapter
import com.spiderverse.whatsappstatusdownload.databinding.FragmentSavedStatusBinding
import com.spiderverse.whatsappstatusdownload.model.StatusModel


class SavedStatusFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentSavedStatusBinding
    private lateinit var sourceDocumentFile: DocumentFile
    private val savedStatusList: MutableList<StatusModel> = mutableListOf()
    private var savedStatusAdapter: SavedStatusAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentSavedStatusBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchSavedStatus()
        fragmentBinding.refreshSavedStatus.setOnRefreshListener {
            fetchSavedStatus()
            fragmentBinding.refreshSavedStatus.isRefreshing = false
        }
    }

    private fun fetchSavedStatus() {
        check()

        if (checkSourceFolderExists()) {
            addDataInList()

        } else {
            showEmptyFileLayout()
        }
    }

    private fun showEmptyFileLayout() {
        fragmentBinding.rvSavedStatus.visibility = View.GONE
        fragmentBinding.tvMessage.visibility = View.VISIBLE
    }

    private fun addDataInList() {

        val list: List<UriPermission> =
            requireActivity().contentResolver.persistedUriPermissions

        val file: DocumentFile? = DocumentFile.fromTreeUri(requireActivity(), list[0].uri)

        savedStatusList.clear()

        if (file == null) {
//                mainHandler.post {
            showEmptyFileLayout()
//                }
            return
        }

        val savedStatusFiles = file.listFiles()

        if (savedStatusFiles.isEmpty()) {
            showEmptyFileLayout()
            return
        }

        for (documentFile in savedStatusFiles) {
            val savedStatus = StatusModel(documentFile)
            savedStatusList.add(savedStatus)
        }

        if (savedStatusList.isEmpty()) showEmptyFileLayout()
        else {
            if (savedStatusAdapter == null) {
                savedStatusAdapter = SavedStatusAdapter(requireActivity(), savedStatusList)
                fragmentBinding.rvSavedStatus.adapter = savedStatusAdapter
            }
            savedStatusAdapter!!.notifyItemRangeChanged(0, savedStatusList.size)
        }
        fragmentBinding.refreshSavedStatus.isRefreshing = false
    }

    private fun checkSourceFolderExists(): Boolean {
        // get external storage directory DocumentFile
        val externalStorageDocumentFile =
            DocumentFile.fromFile(Environment.getExternalStorageDirectory())

        if (externalStorageDocumentFile.findFile("Download") == null) return false

        val downloadDocumentFile =
            checkFolderExist(externalStorageDocumentFile, "Download") ?: return false

//        val sourceDocumentFile =
        return (checkFolderExist(downloadDocumentFile, "WhatsAppStatusSaver") != null)

//        return true
    }

    private fun checkFolderExist(parentFolder: DocumentFile, folderName: String): DocumentFile? {
        return parentFolder.findFile(folderName)
    }

    private fun check() {


    }
}