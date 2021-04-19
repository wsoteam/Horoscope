package com.wsoteam.horoscopes.presentation.match.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.match_fragment.*
import kotlinx.android.synthetic.main.unlock_dialog.*

class UnlockDialog  : DialogFragment(){

    companion object{

        const val OWN_IMG_ID_TAG = "OWN_IMG_ID_TAG"
        const val OWN_SIGN_NAME_TAG = "OWN_SIGN_NAME_TAG"
        const val MATCH_IMG_ID_TAG = "MATCH_IMG_ID_TAG"
        const val MATCH_SIGN_NAME_TAG = "MATCH_SIGN_NAME_TAG"

        fun newInstance(ownImgId : Int, ownSignName : String, matchImgId : Int, matchSignName : String) : UnlockDialog{
            var bundle = Bundle()
            bundle.putInt(OWN_IMG_ID_TAG, ownImgId)
            bundle.putInt(MATCH_IMG_ID_TAG, matchImgId)
            bundle.putString(OWN_SIGN_NAME_TAG, ownSignName)
            bundle.putString(MATCH_SIGN_NAME_TAG, matchSignName)
            return UnlockDialog().also {
                it.arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.unlock_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnShow.setOnClickListener {

        }

        btnPrem.setOnClickListener {

        }

    }
}