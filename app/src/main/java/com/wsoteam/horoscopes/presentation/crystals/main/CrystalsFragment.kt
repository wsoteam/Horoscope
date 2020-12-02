package com.wsoteam.horoscopes.presentation.crystals.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.Config
import com.wsoteam.horoscopes.presentation.crystals.charge.ChargeActivity
import com.wsoteam.horoscopes.presentation.crystals.main.controller.TypeAdapter
import com.wsoteam.horoscopes.presentation.crystals.main.pager.CrystalPageFragment
import com.wsoteam.horoscopes.presentation.crystals.main.pager.CrystalsPagerAdapter
import com.wsoteam.horoscopes.presentation.crystals.shop.ListActivity
import kotlinx.android.synthetic.main.crystals_fragment.*

class CrystalsFragment : Fragment(R.layout.crystals_fragment) {

    lateinit var typeAdapter: TypeAdapter

    lateinit var idsTypeImgs: IntArray
    lateinit var idsCrystalsImgs: IntArray

    lateinit var affirsListsIds: List<Int>

    lateinit var typeNames: Array<String>
    lateinit var crystalsNames: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        typeNames = resources.getStringArray(R.array.crystals_prop)
        crystalsNames = resources.getStringArray(R.array.crystals_names)
        idsTypeImgs = getIndexes(typeNames.size, R.array.types_ids)
        idsCrystalsImgs = getIndexes(typeNames.size, R.array.crystals_ids)

        affirsListsIds = listOf(
            R.array.crystal_afir_0,
            R.array.crystal_afir_1,
            R.array.crystal_afir_2,
            R.array.crystal_afir_3,
            R.array.crystal_afir_4,
            R.array.crystal_afir_5,
            R.array.crystal_afir_6,
            R.array.crystal_afir_7
        )

        rvTypes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        typeAdapter = TypeAdapter(idsTypeImgs, typeNames)
        rvTypes.adapter = typeAdapter

        vpCrystals.adapter = CrystalsPagerAdapter(getCrystalsList(), childFragmentManager)

        btnChargeCrystal.setOnClickListener {
            openChargeActivity()
        }

        llShop.setOnClickListener {
            startActivity(Intent(activity, ListActivity::class.java))
        }

    }

    private fun openChargeActivity() {
        var intent = Intent(activity, ChargeActivity::class.java)
        var crystalNumber = 2
        intent.putExtra(Config.TAG_TYPE_NAME, typeNames[crystalNumber])
        intent.putExtra(Config.TAG_CRYSTAL_IMG_ID, idsCrystalsImgs[crystalNumber])
        intent.putExtra(Config.TAG_TYPE_IMG_ID, idsTypeImgs[crystalNumber])
        intent.putExtra(Config.TAG_AFFIR_LIST, affirsListsIds[crystalNumber])
        startActivity(intent)
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