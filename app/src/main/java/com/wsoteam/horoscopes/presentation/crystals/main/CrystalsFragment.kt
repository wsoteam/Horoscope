package com.wsoteam.horoscopes.presentation.crystals.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.main.controller.TypeAdapter
import com.wsoteam.horoscopes.presentation.crystals.main.pager.CrystalPageFragment
import com.wsoteam.horoscopes.presentation.crystals.main.pager.CrystalsPagerAdapter
import kotlinx.android.synthetic.main.crystals_fragment.*

class CrystalsFragment : Fragment(R.layout.crystals_fragment) {

    lateinit var typeAdapter: TypeAdapter

    lateinit var idsTypeImgs: IntArray
    lateinit var idsCrystalsImgs: IntArray
    lateinit var typeNames: Array<String>
    lateinit var crystalsNames: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        typeNames = resources.getStringArray(R.array.crystals_prop)
        crystalsNames = resources.getStringArray(R.array.crystals_names)
        idsTypeImgs = getIndexes(typeNames.size, R.array.types_ids)
        idsCrystalsImgs = getIndexes(typeNames.size, R.array.crystals_ids)

        rvTypes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        typeAdapter = TypeAdapter(idsTypeImgs, typeNames)
        rvTypes.adapter = typeAdapter

        vpCrystals.adapter = CrystalsPagerAdapter(getCrystalsList(), childFragmentManager)

    }

    private fun getCrystalsList(): List<Fragment> {
        var fragmentList = arrayListOf<Fragment>()
        for (i in idsCrystalsImgs.indices) {
            fragmentList.add(CrystalPageFragment.newInstance(idsCrystalsImgs[i], crystalsNames[i]))
        }
        return fragmentList
    }


    private fun getIndexes(size: Int, arrayId: Int): IntArray {
        var indexes = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
        for (i in 0 until size) {
            indexes[i] = resources.obtainTypedArray(arrayId).getResourceId(i, -1)
        }
        return indexes
    }
}