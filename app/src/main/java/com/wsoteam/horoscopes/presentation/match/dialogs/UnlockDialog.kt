package com.wsoteam.horoscopes.presentation.match.dialogs

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.MatchPair.MatchPair
import kotlinx.android.synthetic.main.unlock_dialog.*
import kotlinx.android.synthetic.main.unlock_dialog.view.*

class UnlockDialog : DialogFragment() {

    interface Callbacks {
        fun showAd()
        fun unlockPrem()
    }

    lateinit var matchPair: MatchPair

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.unlock_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))

        matchPair = arguments!!.getSerializable(MATCH_PAIR_TAG) as MatchPair

        view.ivOwnSign.setImageResource(matchPair.ownImgId)
        view.ivOwnSign.isEnabled = false
        view.tvOwnSign.text = matchPair.ownSignName

        view.ivMatchSign.setImageResource(matchPair.matchImgId)
        view.tvMatchSign.text = matchPair.matchSignName

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnShowAd.setOnClickListener {
            (targetFragment as Callbacks).showAd()
            dismiss()
        }

        btnPrem.setOnClickListener {
            (targetFragment as Callbacks).unlockPrem()
            dismiss()
        }

    }

    companion object {

        const val MATCH_PAIR_TAG = "MATCH_PAIR_TAG"

        fun newInstance(
            matchPair: MatchPair
        ): UnlockDialog {
            var bundle = Bundle().apply {
                putSerializable(MATCH_PAIR_TAG, matchPair)
            }
            return UnlockDialog().apply {
                arguments = bundle
            }
        }
    }
}