package com.wsoteam.horoscopes.presentation.crystals.shop.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.dialog_date.*
import kotlinx.android.synthetic.main.dialog_success.view.*

class DialogSuccess : DialogFragment() {

    companion object {
        const val ID_TAG = "ID_TAG"
        const val NAME_TAG = "NAME_TAG"

        fun newInstance(id : Int, name : String) : DialogSuccess {
            var bundle = Bundle()
            bundle.putInt(ID_TAG, id)
            bundle.putString(NAME_TAG, name)
            var dialog = DialogSuccess()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_success, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        view.tvCrystalText.text = getString(R.string.alert_crystal_text, requireArguments().getString(NAME_TAG))
        view.ivAlertCrystal.setImageResource(requireArguments().getInt(ID_TAG))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}