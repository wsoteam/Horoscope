package com.wsoteam.horoscopes.presentation.premium.ab.dialogs

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.premium.PaySuccessActivity
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.cat_fragment_dialog.view.*

class CatDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.cat_fragment_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        Analytic.showPrem(PreferencesProvider.getVersion()!!)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.btnSkip.setOnClickListener {
            dismiss()
        }

        view.btnPay.setOnClickListener {
            SubscriptionProvider.startChoiseSub((activity as MainActivity), Config.ALERT_SUB, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }
    }

    private fun handlInApp() {
        FirebaseAnalytics.getInstance(requireContext()).logEvent("trial", null)
        FBAnalytic.logTrial(requireContext())
        Analytic.makePurchase(PreferencesProvider.getVersion()!!, "form")
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(activity, PaySuccessActivity::class.java))
        activity!!.finish()
    }
}