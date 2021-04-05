package com.wsoteam.horoscopes.presentation.onboard.pager.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.birth_time_fragment.*

class BirthTimeFragment : Fragment(R.layout.birth_time_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        npHours.maxValue = 23
        npHours.minValue = 0

        npMinutes.maxValue = 59
        npMinutes.minValue = 0

        npHours.value = 17
        npMinutes.value = 33

    }
}