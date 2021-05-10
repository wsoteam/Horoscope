package com.wsoteam.horoscopes.presentation.profile.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.date_fragment.*

class DateFragment : DialogFragment() {

    interface Callbacks{
        fun setNewBirthday(date : String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.date_fragment, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var day = arguments!!.getInt(DAY_TAG)
        var month = arguments!!.getInt(MONTH_TAG)
        var year = arguments!!.getInt(YEAR_TAG)

        dpCalendar.updateDate(year, month - 1, day)

        tvCancel.setOnClickListener {
            dismiss()
        }
        tvOk.setOnClickListener {
            (targetFragment as Callbacks).setNewBirthday(getDate())
            dismiss()
        }
    }

    private fun getDate(): String {
        return "${String.format("%02d", dpCalendar.dayOfMonth)}.${String.format("%02d", dpCalendar.month + 1)}.${dpCalendar.year}"
    }

    companion object{
        private const val DAY_TAG = "DAY_TAG"
        private const val MONTH_TAG = "MONTH_TAG"
        private const val YEAR_TAG = "YEAR_TAG"

        fun newInstance(day : Int, month : Int, year : Int) : DateFragment{
            var args = Bundle().apply {
                putInt(DAY_TAG, day)
                putInt(MONTH_TAG, month)
                putInt(YEAR_TAG, year)
            }
            return DateFragment().apply {
                arguments = args
            }
        }
    }
}