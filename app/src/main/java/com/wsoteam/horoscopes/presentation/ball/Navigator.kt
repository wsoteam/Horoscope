package com.wsoteam.horoscopes.presentation.ball

import androidx.fragment.app.Fragment

interface Navigator {
    fun addBackStackFragment(fragment: Fragment)
    fun replaceFragment(fragment: Fragment)
}