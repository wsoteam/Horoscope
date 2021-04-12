package com.wsoteam.horoscopes.presentation.onboard.prem.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.FormActivity
import com.wsoteam.horoscopes.presentation.onboard.prem.EnterActivity
import com.wsoteam.horoscopes.presentation.settings.SettingsFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.dialog_date.*
import kotlinx.android.synthetic.main.quest_dialog.*

class QuestDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.quest_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        flYes.setOnClickListener {
            (activity as EnterActivity).openNextScreen()
            dismiss()
        }

        flNo.setOnClickListener {
            dismiss()
        }
    }

}