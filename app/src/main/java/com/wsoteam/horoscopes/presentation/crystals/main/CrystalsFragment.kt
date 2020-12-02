package com.wsoteam.horoscopes.presentation.crystals.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.Config
import com.wsoteam.horoscopes.presentation.crystals.charge.ChargeActivity
import com.wsoteam.horoscopes.presentation.crystals.main.controller.TypeAdapter
import com.wsoteam.horoscopes.presentation.crystals.main.pager.CrystalPageFragment
import com.wsoteam.horoscopes.presentation.crystals.main.pager.CrystalsPagerAdapter
import com.wsoteam.horoscopes.presentation.crystals.shop.ListActivity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.crystals_fragment.*

class CrystalsFragment : Fragment(R.layout.crystals_fragment) {

    lateinit var typeAdapter: TypeAdapter

    lateinit var idsTypeImgs: IntArray
    lateinit var idsCrystalsImgs: IntArray

    lateinit var affirsListsIds: List<Int>

    lateinit var typeNames: Array<String>
    lateinit var crystalsNames: Array<String>
    lateinit var inappids: Array<String>

    var buyedIds = arrayListOf<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        typeNames = resources.getStringArray(R.array.crystals_prop)
        inappids = resources.getStringArray(R.array.sub_ids)
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

        btnChargeCrystal.setOnClickListener {
            openChargeActivity(buyedIds[vpCrystals.currentItem])
        }

        llShop.setOnClickListener {
            startActivity(Intent(activity, ListActivity::class.java))
        }
        vpCrystals.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
            }
        })

    }

    override fun onResume() {
        super.onResume()
        fillExistIds()
        vpCrystals.adapter = CrystalsPagerAdapter(getCrystalsList(), childFragmentManager)
        typeAdapter = TypeAdapter(getTypesImgs(), getTypesNames())
        rvTypes.adapter = typeAdapter
    }

    private fun getTypesNames(): ArrayList<String> {
        var namesList = arrayListOf<String>()
        for (i in buyedIds.indices){
            namesList.add(typeNames[buyedIds[i]])
        }
        return namesList
    }

    private fun getTypesImgs(): ArrayList<Int> {
        var imgsArray = arrayListOf<Int>()
        for (i in buyedIds.indices){
            imgsArray.add(idsTypeImgs[buyedIds[i]])
        }
        return imgsArray
    }

    private fun fillExistIds() {
        buyedIds = arrayListOf()
        for (i in inappids.indices){
            if (PreferencesProvider.getInapp(inappids[i]) != PreferencesProvider.EMPTY_INAPP){
                buyedIds.add(i)
            }
        }
    }

    private fun openChargeActivity(crystalNumber : Int) {
        var intent = Intent(activity, ChargeActivity::class.java)
        intent.putExtra(Config.TAG_TYPE_NAME, typeNames[crystalNumber])
        intent.putExtra(Config.TAG_CRYSTAL_IMG_ID, idsCrystalsImgs[crystalNumber])
        intent.putExtra(Config.TAG_TYPE_IMG_ID, idsTypeImgs[crystalNumber])
        intent.putExtra(Config.TAG_AFFIR_LIST, affirsListsIds[crystalNumber])
        startActivity(intent)
    }

    private fun getCrystalsList(): List<Fragment> {
        var fragmentList = arrayListOf<Fragment>()
        for (i in buyedIds.indices) {
            fragmentList.add(CrystalPageFragment.newInstance(idsCrystalsImgs[buyedIds[i]], crystalsNames[buyedIds[i]]))
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