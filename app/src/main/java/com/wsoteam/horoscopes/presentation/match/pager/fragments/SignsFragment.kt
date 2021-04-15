package com.wsoteam.horoscopes.presentation.match.pager.fragments

import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.match.MatchFragment
import com.wsoteam.horoscopes.presentation.match.controller.IClick
import com.wsoteam.horoscopes.presentation.match.controller.MatchSignsAdapter
import kotlinx.android.synthetic.main.signs_fragment.*

class SignsFragment : Fragment(R.layout.signs_fragment) {

    lateinit var imgsArray: TypedArray
    lateinit var signsNames: Array<String>
    lateinit var rvAdapter: MatchSignsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgsArray = resources.obtainTypedArray(R.array.match_signs_imgs)
        signsNames = resources.getStringArray(R.array.match_signs_names)

        rvAdapter = MatchSignsAdapter(imgsArray, signsNames, object : IClick {
            override fun onClick(position: Int) {
                (parentFragment as MatchFragment).setMatchSign(position)
            }
        })

        rvSigns.layoutManager = GridLayoutManager(activity, 2)
        rvSigns.adapter = rvAdapter
    }
}