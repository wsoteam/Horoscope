package com.wsoteam.horoscopes.presentation.crystals.main.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.end_dialog.*
import kotlinx.android.synthetic.main.end_dialog.view.*

class EndDialog : DialogFragment() {

    companion object {
        private const val TAG_NAME_CRYSTAL = "TAG_NAME_CRYSTAL"
        private const val TAG_DOWN_TIME = "TAG_DOWN_TIME"

        fun newInstance(time: String, name: String): EndDialog {
            var args = Bundle()
            args.putString(TAG_NAME_CRYSTAL, name)
            args.putString(TAG_DOWN_TIME, time)
            var fragment = EndDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.end_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))

        tvEndTitle.text = view.context.getString(
            R.string.the_effect_of_the,
            requireArguments().getString(TAG_NAME_CRYSTAL, ""),
            requireArguments().getString(TAG_DOWN_TIME, "")
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvOkEndCrystal.setOnClickListener {
            dismiss()
        }
    }
}