package com.wsoteam.horoscopes.presentation.crystals.charge.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.charge.ChargeActivity
import kotlinx.android.synthetic.main.charge_interrupt_dialog.*

class ChargeInterruptDialog : DialogFragment() {

    var isBack = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.charge_interrupt_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvBack.setOnClickListener {
            dismiss()
        }
        tvYes.setOnClickListener {
            (activity as ChargeActivity).closeActivity()
            dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as ChargeActivity).resumeCharge()
    }

    override fun onResume() {
        super.onResume()
        (activity as ChargeActivity).pauseCharge()
    }
}