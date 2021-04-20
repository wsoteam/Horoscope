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
import kotlinx.android.synthetic.main.unlock_dialog.*
import kotlinx.android.synthetic.main.unlock_dialog.view.*

class UnlockDialog : DialogFragment() {

    interface Callbacks{
        fun showAd()
        fun unlockPrem()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.unlock_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))

        view.ivOwnSign.setImageResource(arguments!!.getInt(OWN_IMG_ID_TAG))
        view.ivOwnSign.isEnabled = false
        view.tvOwnSign.text = arguments!!.getString(OWN_SIGN_NAME_TAG)

        view.ivMatchSign.setImageResource(arguments!!.getInt(MATCH_IMG_ID_TAG))
        view.tvMatchSign.text = arguments!!.getString(MATCH_SIGN_NAME_TAG)


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

        const val OWN_IMG_ID_TAG = "OWN_IMG_ID_TAG"
        const val OWN_SIGN_NAME_TAG = "OWN_SIGN_NAME_TAG"
        const val MATCH_IMG_ID_TAG = "MATCH_IMG_ID_TAG"
        const val MATCH_SIGN_NAME_TAG = "MATCH_SIGN_NAME_TAG"

        fun newInstance(
            ownImgId: Int,
            ownSignName: String,
            matchImgId: Int,
            matchSignName: String
        ): UnlockDialog {
            var bundle = Bundle().apply {
                putInt(OWN_IMG_ID_TAG, ownImgId)
                putInt(MATCH_IMG_ID_TAG, matchImgId)
                putString(OWN_SIGN_NAME_TAG, ownSignName)
                putString(MATCH_SIGN_NAME_TAG, matchSignName)
            }
            return UnlockDialog().apply {
                arguments = bundle
            }
        }
    }
}