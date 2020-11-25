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
import kotlinx.android.synthetic.main.charge_success_dialog.*

class ChargeSuccessDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.charge_success_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvOkChargeSuccess.setOnClickListener {
            (activity as ChargeActivity).closeActivity()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as ChargeActivity).pauseCharge()
    }
}