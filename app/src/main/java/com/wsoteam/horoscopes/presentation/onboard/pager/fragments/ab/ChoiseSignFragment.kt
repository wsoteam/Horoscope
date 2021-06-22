package com.wsoteam.horoscopes.presentation.onboard.pager.fragments.ab

import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.info.controller.InfoSignAdapter
import com.wsoteam.horoscopes.presentation.onboard.pager.fragments.ab.controller.ChoiseSignAdapter
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.choise_fragment.*

class ChoiseSignFragment : Fragment(R.layout.choise_fragment) {

    private lateinit var signImgs: TypedArray
    private lateinit var signNames: Array<String>
    private lateinit var dates: ArrayList<String>

    private lateinit var adapter: ChoiseSignAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillDates()
        signNames = resources.getStringArray(R.array.names_signs)
        rvSigns.layoutManager = GridLayoutManager(requireContext(), 4)
        adapter = ChoiseSignAdapter(getImgs(), signNames)
        rvSigns.adapter = adapter

    }

    private fun fillDates() {
        dates = arrayListOf()
        dates.add("25.03.1990")
        dates.add("25.04.1990")
        dates.add("25.05.1990")
        dates.add("25.06.1990")
        dates.add("25.07.1990")
        dates.add("25.08.1990")
        dates.add("25.09.1990")
        dates.add("25.10.1990")
        dates.add("25.11.1990")
        dates.add("25.12.1990")
        dates.add("25.01.1990")
        dates.add("25.02.1990")
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

    fun saveData() {
        var birthDay = dates[adapter.getSelectedItem()]
        PreferencesProvider.setBirthday(birthDay)
    }


}