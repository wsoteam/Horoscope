package com.wsoteam.horoscopes.presentation.onboard.prem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.BlackMainActivity
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.prem.dialogs.QuestDialog
import com.wsoteam.horoscopes.presentation.premium.SubsIds
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import com.wsoteam.horoscopes.utils.choiceSign
import kotlinx.android.synthetic.main.enter_prem_activity.*
import kotlinx.android.synthetic.main.green_prem_activity.*
import java.lang.Exception
import java.text.DecimalFormat

class EnterActivity : AppCompatActivity(R.layout.enter_prem_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPrice()
        setUserData()

        ivClose.setOnClickListener {
            QuestDialog().show(supportFragmentManager, "")
        }


        btnTrial.setOnClickListener {
            SubscriptionProvider.startChoiseSub(this, SubsIds.HAND_SCAN, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

        if (intent?.getBooleanExtra(FROM_TAG, false) == true){
            ivClose.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setUserData() {
        var signName = resources.getStringArray(R.array.names_signs)[choiceSign(PreferencesProvider.getBirthday()!!)]
        tvTitle.text = getString(R.string.prem_onboard_info, PreferencesProvider.getName(), signName)
    }

    private fun setPrice(){
        try {
            var unit = PreferencesProvider.getPriceUnit()!!
            var price = PreferencesProvider.getPriceValue()!!.toDouble() / 1_000_000
            var diff = price / 5

            var oldPrice = diff + price

            var formatter = DecimalFormat("#0.00")

            tvTrial.text = getString(R.string.after_trial_ends_449_00_week, "${formatter.format(price)} $unit")
        }catch (ex : Exception){
            Log.e("LOL", ex.message)
        }
    }


    fun openNextScreen(){
        startActivity(Intent(this, FinishActivity::class.java))
        finish()
    }

    private fun handlInApp() {
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(this, BlackMainActivity::class.java))
        finishAffinity()
    }

    companion object{
        private const val FROM_TAG = "FROM_TAG"

        fun getIntent(context: Context, isFromMain : Boolean) : Intent{
            return Intent(context, EnterActivity::class.java).apply {
                putExtra(FROM_TAG, isFromMain)
            }
        }
    }
}