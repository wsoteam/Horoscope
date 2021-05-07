package com.wsoteam.horoscopes.presentation.profile.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.MatchPair.MatchPair
import com.wsoteam.horoscopes.presentation.match.dialogs.UnlockDialog
import kotlinx.android.synthetic.main.unlock_dialog.view.*

class DateFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.date_fragment, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }
}