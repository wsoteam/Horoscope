package com.wsoteam.horoscopes.presentation.crystals.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.viewpager.widget.ViewPager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.Config
import com.wsoteam.horoscopes.presentation.crystals.charge.ChargeActivity
import com.wsoteam.horoscopes.presentation.crystals.main.controller.TypeAdapter
import com.wsoteam.horoscopes.presentation.crystals.main.dialogs.EndDialog
import com.wsoteam.horoscopes.presentation.crystals.main.pager.CrystalPageFragment
import com.wsoteam.horoscopes.presentation.crystals.main.pager.CrystalsPagerAdapter
import com.wsoteam.horoscopes.presentation.crystals.shop.ListActivity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.crystaltimer.CrystalTimer
import com.wsoteam.horoscopes.utils.crystaltimer.ICrystalTimer
import kotlinx.android.synthetic.main.crystals_fragment.*

class CrystalsFragment : Fragment(R.layout.crystals_fragment) {

    lateinit var typeAdapter: TypeAdapter

    lateinit var idsTypeImgs: IntArray
    lateinit var idsCrystalsImgs: IntArray

    lateinit var affirsListsIds: List<Int>

    lateinit var typeNames: Array<String>
    lateinit var crystalsNames: Array<String>
    lateinit var inappids: Array<String>

    var buyedCrystalsNumbers = arrayListOf<Int>()

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
            openChargeActivity(buyedCrystalsNumbers[vpCrystals.currentItem])
        }

        llShop.setOnClickListener {
            startActivity(Intent(activity, ListActivity::class.java))
        }

        ivNextCrystal.setOnClickListener {
            vpCrystals.currentItem = vpCrystals.currentItem + 1
        }

        ivPrevCrystal.setOnClickListener {
            vpCrystals.currentItem = vpCrystals.currentItem - 1
        }
    }

    override fun onResume() {
        super.onResume()
        ivPrevCrystal.setImageResource(R.drawable.ic_crystal_prev_inactive)
        ivPrevCrystal.isActivated = false

        fillExistIds()

        vpCrystals.adapter = CrystalsPagerAdapter(getCrystalsList(), childFragmentManager)
        typeAdapter = TypeAdapter(getTypesImgs(), getTypesNames())

        rvTypes.adapter = typeAdapter

        refreshCrystalState(vpCrystals.currentItem)

        vpCrystals.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (buyedCrystalsNumbers.size > 0) {
                    if (position == 0) {
                        ivPrevCrystal.setImageResource(R.drawable.ic_crystal_prev_inactive)
                        ivPrevCrystal.isActivated = false
                    } else {
                        ivPrevCrystal.setImageResource(R.drawable.ic_crystal_prev_active)
                        ivPrevCrystal.isActivated = true
                    }

                    if (position == buyedCrystalsNumbers.size - 1) {
                        ivNextCrystal.setImageResource(R.drawable.ic_crystal_next_inactive)
                        ivNextCrystal.isActivated = false
                    } else {
                        ivNextCrystal.setImageResource(R.drawable.ic_crystal_next_active)
                        ivNextCrystal.isActivated = true
                    }
                    refreshCrystalState(position)
                }
            }
        })
    }

    private fun markTimerNotEnd() {
        var drawable =
            DrawableCompat.wrap(requireContext().getDrawable(R.drawable.ic_countdown_crystal)!!)
        DrawableCompat.setTint(drawable, resources.getColor(R.color.white))
        ivCountdownCrystal.setImageDrawable(drawable)
        tvCountdownCrystal.setTextColor(requireContext().resources.getColor(R.color.white))
    }

    private fun markTimerEnd() {
        var drawable =
            DrawableCompat.wrap(requireContext().getDrawable(R.drawable.ic_countdown_crystal)!!)
        DrawableCompat.setTint(drawable, resources.getColor(R.color.end_timer))
        ivCountdownCrystal.setImageDrawable(drawable)
        tvCountdownCrystal.setTextColor(requireContext().resources.getColor(R.color.end_timer))
    }


    private fun refreshCrystalState(position: Int) {
        CrystalTimer.unObserve()
        CrystalTimer.init(buyedCrystalsNumbers[position], object : ICrystalTimer {
            override fun setState(state: Int) {
                when (state) {
                    CrystalTimer.ENDING_STATE -> markTimerEnd()
                    CrystalTimer.NOT_ENDING_STATE -> markTimerNotEnd()
                    CrystalTimer.END_TIME -> {
                    }
                    CrystalTimer.IS_CONSUMED -> {

                    }
                    CrystalTimer.ALERT_TIME -> {
                        markTimerEnd()
                        EndDialog.newInstance(CrystalTimer.getAlertTime(), )
                    }
                }
            }

            override fun refreshTime(time: String) {
                tvCountdownCrystal.text = time
            }
        })
    }

    private fun getTypesNames(): ArrayList<String> {
        var namesList = arrayListOf<String>()
        for (i in buyedCrystalsNumbers.indices) {
            namesList.add(typeNames[buyedCrystalsNumbers[i]])
        }
        return namesList
    }

    private fun getTypesImgs(): ArrayList<Int> {
        var imgsArray = arrayListOf<Int>()
        for (i in buyedCrystalsNumbers.indices) {
            imgsArray.add(idsTypeImgs[buyedCrystalsNumbers[i]])
        }
        return imgsArray
    }

    private fun fillExistIds() {
        buyedCrystalsNumbers = arrayListOf()
        for (i in inappids.indices) {
            if (PreferencesProvider.getInapp(inappids[i]) != PreferencesProvider.EMPTY_INAPP) {
                buyedCrystalsNumbers.add(i)
            }
        }
    }

    private fun openChargeActivity(crystalNumber: Int) {
        var intent = Intent(activity, ChargeActivity::class.java)
        intent.putExtra(Config.TAG_TYPE_NAME, typeNames[crystalNumber])
        intent.putExtra(Config.TAG_CRYSTAL_IMG_ID, idsCrystalsImgs[crystalNumber])
        intent.putExtra(Config.TAG_TYPE_IMG_ID, idsTypeImgs[crystalNumber])
        intent.putExtra(Config.TAG_AFFIR_LIST, affirsListsIds[crystalNumber])
        startActivity(intent)
    }

    private fun getCrystalsList(): List<Fragment> {
        var fragmentList = arrayListOf<Fragment>()
        for (i in buyedCrystalsNumbers.indices) {
            fragmentList.add(
                CrystalPageFragment.newInstance(
                    idsCrystalsImgs[buyedCrystalsNumbers[i]],
                    crystalsNames[buyedCrystalsNumbers[i]]
                )
            )
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