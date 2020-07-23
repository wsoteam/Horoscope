package com.wsoteam.horoscopes.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.main.controller.HoroscopeAdapter
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(R.layout.main_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMain.layoutManager = LinearLayoutManager(this.context)
        rvMain.adapter = HoroscopeAdapter()
    }
}