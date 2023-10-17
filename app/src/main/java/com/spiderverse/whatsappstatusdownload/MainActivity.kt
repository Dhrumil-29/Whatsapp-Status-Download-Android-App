package com.spiderverse.whatsappstatusdownload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spiderverse.whatsappstatusdownload.adapter.ViewPagerAdapter
import com.spiderverse.whatsappstatusdownload.databinding.ActivityMainBinding
import com.spiderverse.whatsappstatusdownload.fragments.ImageStatusFragment
import com.spiderverse.whatsappstatusdownload.fragments.SavedStatusFragment
import com.spiderverse.whatsappstatusdownload.fragments.VideoStatusFragment
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewPagerAdapter:ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setContentView(binding.root)
    }

    private fun initView(){
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ImageStatusFragment(),"Images")
        viewPagerAdapter.addFragment(VideoStatusFragment(),"Videos")
        viewPagerAdapter.addFragment(SavedStatusFragment(),"Saved")

        binding.viewpager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewpager,true)

        setSupportActionBar(binding.appToolbar)
    }
}