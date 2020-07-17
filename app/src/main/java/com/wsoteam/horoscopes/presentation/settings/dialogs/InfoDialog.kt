package com.wsoteam.horoscopes.presentation.settings.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.FormActivity
import kotlinx.android.synthetic.main.dialog_date.*
import kotlinx.android.synthetic.main.dialog_date.tvOk
import kotlinx.android.synthetic.main.dialog_info.*

class InfoDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_info, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvOk.setOnClickListener {
            dismiss()
        }
    }
}