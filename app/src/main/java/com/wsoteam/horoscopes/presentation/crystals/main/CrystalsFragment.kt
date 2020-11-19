package com.wsoteam.horoscopes.presentation.crystals.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.main.controller.TypeAdapter
import kotlinx.android.synthetic.main.crystals_fragment.*

class CrystalsFragment : Fragment(R.layout.crystals_fragment) {

    lateinit var typeAdapter: TypeAdapter

    lateinit var idsTypeImgs: IntArray
    lateinit var typeNames: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        typeNames = resources.getStringArray(R.array.crystals_prop)
        idsTypeImgs = getIndexes(typeNames.size, R.array.types_ids)

        rvTypes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        typeAdapter = TypeAdapter(idsTypeImgs, typeNames)
        rvTypes.adapter = typeAdapter

    }


    private fun getIndexes(size: Int, arrayId: Int): IntArray {
        var indexes = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
        for (i in 0 until size) {
            indexes[i] = resources.obtainTypedArray(arrayId).getResourceId(i, -1)
        }
        return indexes
    }
}