package com.wsoteam.horoscopes.presentation.hand.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.unlock_scan_dialog.*

class UnlockScanDialog : DialogFragment() {

    interface Callbacks {
        fun showAd()
        fun unlockPrem()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.unlock_scan_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        dialog?.setCancelable(false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnShowAd.setOnClickListener {
            (targetFragment as Callbacks).showAd()
        }

        btnPrem.setOnClickListener {
            (targetFragment as Callbacks).unlockPrem()
        }

    }


}