package com.wsoteam.horoscopes.presentation.crystals.main.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CrystalsPagerAdapter(var fragmentsList : List<Fragment>, fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }
}