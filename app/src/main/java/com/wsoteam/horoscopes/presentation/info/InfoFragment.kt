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

        //signImgs = resources.obtainTypedArray(R.array.sign_draws_info)
        signNames = resources.getStringArray(R.array.names_signs)

        adapter = InfoSignAdapter(getImgs(), signNames, object : Callbacks {
            override fun openSign(position: Int) {
                (requireActivity() as InfoFragmentCallbacks).openDescriptionFragment(position)
            }
        })
        rvInfoSigns.layoutManager = GridLayoutManager(requireActivity(), 3)
        rvInfoSigns.adapter = adapter
    }

    private fun getImgs(): ArrayList<Int> {
        var listImgs = arrayListOf<Int>()
        /*for (i in signNames.indices){
            listImgs.add(signImgs.getResourceId(i, -1))
        }*/
        listImgs.add(R.drawable.img_onboard_aries)
        listImgs.add(R.drawable.img_onboard_taurus)
        listImgs.add(R.drawable.img_onboard_gemini)
        listImgs.add(R.drawable.img_onboard_cancer)
        listImgs.add(R.drawable.img_onboard_leo)
        listImgs.add(R.drawable.img_onboard_virgo)
        listImgs.add(R.drawable.img_onboard_libra)
        listImgs.add(R.drawable.img_onboard_scorpio)
        listImgs.add(R.drawable.img_onboard_sagittarius)
        listImgs.add(R.drawable.img_onboard_capricorn)
        listImgs.add(R.drawable.img_onboard_aquarius)
        listImgs.add(R.drawable.img_onboard_pisces)

        return listImgs
    }


    interface InfoFragmentCallbacks {
        fun openDescriptionFragment(index: Int)
    }

}