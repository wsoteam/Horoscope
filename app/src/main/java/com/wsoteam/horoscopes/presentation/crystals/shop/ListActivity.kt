package com.wsoteam.horoscopes.presentation.crystals.shop

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.shop.controller.ListShopAdapter
import com.wsoteam.horoscopes.presentation.crystals.shop.dialogs.DialogSuccess
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.crystal_details.*
import kotlinx.android.synthetic.main.list_activity.*
import java.util.*

class ListActivity : AppCompatActivity(R.layout.list_activity) {

    var imgsIds: IntArray? = null
    var imgIdsType: IntArray? = null
    var alertImgIds: IntArray? = null
    var names: Array<String>? = null
    var props: Array<String>? = null
    var details: Array<String>? = null
    var inappIds: Array<String>? = null

    var adapter : ListShopAdapter? = null

    var bsCrystal : BottomSheetBehavior<LinearLayout>? = null

    var currentId = -1
    var currentInappId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        names = resources.getStringArray(R.array.crystals_names)
        props = resources.getStringArray(R.array.crystals_prop)
        details = resources.getStringArray(R.array.crystals_details)
        inappIds = resources.getStringArray(R.array.sub_ids)
        imgsIds = getIndexes(names!!.size, R.array.crystals_ids)
        imgIdsType = getIndexes(names!!.size, R.array.types_ids)
        alertImgIds = getIndexes(names!!.size, R.array.crystal_alert_ids)

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

        btnActivate.setOnClickListener {
            DialogSuccess.newInstance(alertImgIds!![currentId], names!![currentId]).show(supportFragmentManager, "")
        }

        btnBuyCrystal.setOnClickListener {
            SubscriptionProvider.payItem(this, currentInappId, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }
    }

    private fun handlInApp() {
        //Analytic.makePurchase(PreferencesProvider.getBeforePremium()!!, getPlacement())
        //FirebaseAnalytics.getInstance(requireContext()).logEvent("trial", null)
        //FBAnalytic.logTrial(activity!!)
        //PreferencesProvider.setADStatus(false)
        //openNextScreen()
        PreferencesProvider.setInapp(currentInappId, Calendar.getInstance().timeInMillis)
        DialogSuccess.newInstance(imgsIds!![currentId], names!![currentId]).show(supportFragmentManager, "")
        bsCrystal!!.state = BottomSheetBehavior.STATE_COLLAPSED
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
        currentId = position
        currentInappId = inappIds!![position]
        hideAllPaymentViews()
        showDefaultViews()
    }

    private fun getIndexes(size: Int, arrayId: Int): IntArray? {
        var indexes = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
        for (i in 0 until size){
            indexes[i] = resources.obtainTypedArray(arrayId).getResourceId(i, -1)
        }
        return indexes
    }

    private fun showNotEnabledViews(){
        btnNoEnabled.visibility = View.VISIBLE
    }

    private fun showActivateViews(){
        btnActivate.visibility = View.VISIBLE
    }

    private fun showDefaultViews(){
        btnBuyCrystal.visibility = View.VISIBLE
        btnBuyPremium.visibility = View.VISIBLE
    }

    private fun hideAllPaymentViews(){
        btnNoEnabled.visibility = View.GONE
        btnActivate.visibility = View.GONE
        btnBuyCrystal.visibility = View.GONE
        btnBuyPremium.visibility = View.GONE
    }

}