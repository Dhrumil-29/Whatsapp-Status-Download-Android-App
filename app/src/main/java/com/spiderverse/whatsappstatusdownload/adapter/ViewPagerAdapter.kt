package com.spiderverse.whatsappstatusdownload.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fm: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {
    private var listOfFragmentPage: MutableList<Fragment> = mutableListOf()
    private var listOfFragmentTitle: MutableList<String> = mutableListOf()

    /*override fun getCount(): Int {
        return listOfFragmentPage.size
    }*/

    /*override fun getItem(position: Int): Fragment {
        return listOfFragmentPage[position]
    }*/

    /*override fun getPageTitle(position: Int): CharSequence? {
        return listOfFragmentTitle[position]
    }*/

    fun addFragment(fragment: Fragment, title:String){
        listOfFragmentPage.add(fragment)
        listOfFragmentTitle.add(title)
    }

    override fun getItemCount(): Int {
        return listOfFragmentPage.size
    }

    override fun createFragment(position: Int): Fragment {
        return listOfFragmentPage[position]
    }
}