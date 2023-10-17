package com.spiderverse.whatsappstatusdownload.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var listOfFragmentPage: MutableList<Fragment> = mutableListOf()
    private var listOfFragmentTitle: MutableList<String> = mutableListOf()

    override fun getCount(): Int {
        return listOfFragmentPage.size
    }

    override fun getItem(position: Int): Fragment {
        return listOfFragmentPage[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listOfFragmentTitle[position]
    }

    fun addFragment(fragment: Fragment, title:String){
        listOfFragmentPage.add(fragment)
        listOfFragmentTitle.add(title)
    }
}