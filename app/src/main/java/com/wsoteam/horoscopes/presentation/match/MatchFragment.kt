package com.wsoteam.horoscopes.presentation.match

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.match_fragment.*

class MatchFragment : Fragment(R.layout.match_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSigns.layoutManager = GridLayoutManager(activity, 2)

    }
}