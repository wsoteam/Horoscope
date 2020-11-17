package com.wsoteam.horoscopes.presentation.crystals.shop

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.shop.controller.ListShopAdapter
import kotlinx.android.synthetic.main.crystal_details.*
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity : AppCompatActivity(R.layout.list_activity) {

    var imgsIds: IntArray? = null
    var imgIdsType: IntArray? = null
    var names: Array<String>? = null
    var props: Array<String>? = null
    var details: Array<String>? = null

    var adapter : ListShopAdapter? = null

    var bsCrystal : BottomSheetBehavior<LinearLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        names = resources.getStringArray(R.array.crystals_names)
        props = resources.getStringArray(R.array.crystals_prop)
        details = resources.getStringArray(R.array.crystals_details)
        imgsIds = getIndexes(names!!.size, R.array.crystals_ids)
        imgIdsType = getIndexes(names!!.size, R.array.types_ids)

        adapter = ListShopAdapter(imgsIds!!, names!!, props!!, object : IListShop{
            override fun open(position: Int) {
                fillAndOpenBS(position)
            }
        })
        rvCrystalsShop.layoutManager = GridLayoutManager(this, 2)
        rvCrystalsShop.adapter = adapter

        bsCrystal = BottomSheetBehavior.from(llBSCrystal)
        ivClose.setOnClickListener {
            bsCrystal!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    override fun onBackPressed() {
        if (bsCrystal!!.state == BottomSheetBehavior.STATE_EXPANDED){
            bsCrystal!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }else{
            super.onBackPressed()
        }
    }

    private fun fillAndOpenBS(position: Int) {
        fillBS(position)
        bsCrystal!!.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun fillBS(position: Int) {
        ivCrystal.setImageResource(imgsIds!![position])
        ivType.setImageResource(imgIdsType!![position])
        tvNameCrystal.text = names!![position]
        tvPropCrystal.text = props!![position]
        tvDetails.text = details!![position]
    }

    private fun getIndexes(size: Int, arrayId: Int): IntArray? {
        var indexes = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
        for (i in 0 until size){
            indexes[i] = resources.obtainTypedArray(arrayId).getResourceId(i, -1)
        }
        return indexes
    }
}