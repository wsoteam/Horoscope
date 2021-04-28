package com.wsoteam.horoscopes.presentation.info

import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.info.controller.Callbacks
import com.wsoteam.horoscopes.presentation.info.controller.InfoSignAdapter
import kotlinx.android.synthetic.main.info_fragment.*

class InfoFragment : Fragment(R.layout.info_fragment) {

    private lateinit var signImgs: TypedArray
    private lateinit var signNames: Array<String>

    private lateinit var adapter: InfoSignAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signImgs = resources.obtainTypedArray(R.array.info_signs_imgs)
        signNames = resources.getStringArray(R.array.names_signs)

        adapter = InfoSignAdapter(signImgs, signNames, object : Callbacks {
            override fun openSign(position: Int) {
                (requireActivity() as InfoFragmentCallbacks).openDescriptionFragment(position)
            }
        })
        rvInfoSigns.layoutManager = GridLayoutManager(requireActivity(), 3)
        rvInfoSigns.adapter = adapter
    }


    interface InfoFragmentCallbacks {
        fun openDescriptionFragment(index: Int)
    }

}