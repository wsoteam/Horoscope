package com.wsoteam.horoscopes.presentation.profile.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.time_fragment.*

class TimeDialog : DialogFragment() {

    interface Callbacks {
        fun setNewTime(time: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.time_fragment, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tpBirth.currentHour = arguments!!.getInt(HOUR_TAG)
        tpBirth.currentMinute = arguments!!.getInt(MINUTE_TAG)


        tvCancel.setOnClickListener {
            dismiss()
        }

        tvOk.setOnClickListener {
            (targetFragment as Callbacks).setNewTime("${String.format("%02d", tpBirth.currentHour)}:${String.format("%02d", tpBirth.currentMinute)}")
            dismiss()
        }
    }

    companion object {
        private const val MINUTE_TAG = "MINUTE_TAG"
        private const val HOUR_TAG = "HOUR_TAG"

        fun newInstance(minute: Int, hour: Int): TimeDialog {
            var args = Bundle().apply {
                putInt(MINUTE_TAG, minute)
                putInt(HOUR_TAG, hour)
            }
            return TimeDialog().apply {
                arguments = args
            }
        }
    }
}