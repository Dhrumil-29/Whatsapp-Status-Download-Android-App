package com.spiderverse.whatsappstatusdownload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayoutMediator
import com.spiderverse.whatsappstatusdownload.adapter.ViewPagerAdapter
import com.spiderverse.whatsappstatusdownload.databinding.ActivityMainBinding
import com.spiderverse.whatsappstatusdownload.fragments.ImageStatusFragment
import com.spiderverse.whatsappstatusdownload.fragments.SavedStatusFragment
import com.spiderverse.whatsappstatusdownload.fragments.VideoStatusFragment
import com.spiderverse.whatsappstatusdownload.utils.Utils
import java.text.FieldPosition
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setContentView(binding.root)
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            0 -> "Images"
            1 -> "Videos"
            2 -> "Saved"
            else -> ""
        }
    }

    private fun initView() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPagerAdapter.addFragment(ImageStatusFragment(), "Images")
        viewPagerAdapter.addFragment(VideoStatusFragment(), "Videos")
        viewPagerAdapter.addFragment(SavedStatusFragment(), "Saved")

        binding.viewpager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
//        binding.tabLayout.setupWithViewPager(binding.viewpager,true)

        setSupportActionBar(binding.appToolbar)
    }

    override fun onResume() {
        super.onResume()
        // ask the permission for READ and WRITE access for external storage.
        if(Utils.APP_DIR.isNullOrEmpty()) {
            Utils.APP_DIR = getExternalFilesDir(null)?.path
            Log.d("App Path", Utils.APP_DIR.toString())
        }
    }
}