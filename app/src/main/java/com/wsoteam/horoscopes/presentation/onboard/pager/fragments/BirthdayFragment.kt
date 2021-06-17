package com.wsoteam.horoscopes.presentation.onboard.pager.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.birthday_fragment.*

class BirthdayFragment : Fragment(R.layout.birthday_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtDay.setText("25")
        edtMonth.setText("2")
        edtYear.setText("1993")

        edtDay.isActivated = false
        edtMonth.isActivated = false
        edtYear.isActivated = false
    }

    fun checkFields(): Boolean {
        var isCorrectDate = true
        if(edtDay.text.toString() == "" || edtDay.text.toString() == "" || edtDay.text.toString().toInt() < 1 || edtDay.text.toString().toInt() > 31){
            isCorrectDate = false
            edtDay.isActivated = true
            tvWarning.visibility = View.VISIBLE
        }

        if(edtMonth.text.toString() == "" || edtMonth.text.toString() == "" || edtMonth.text.toString().toInt() < 1 || edtMonth.text.toString().toInt() > 12){
            isCorrectDate = false
            edtMonth.isActivated = true
            tvWarning.visibility = View.VISIBLE
        }

        if(edtYear.text.toString() == "" || edtYear.text.toString() == "" || edtYear.text.toString().toInt() < 1910 || edtYear.text.toString().toInt() > 2021){
            isCorrectDate = false
            edtYear.isActivated = true
            tvWarning.visibility = View.VISIBLE
        }

        if (isCorrectDate){
            edtDay.isActivated = false
            edtMonth.isActivated = false
            edtYear.isActivated = false
            tvWarning.visibility = View.INVISIBLE
        }

        return isCorrectDate
    }

    fun saveData(){
        var birthDay = "${edtDay.text}.${edtMonth.text}.${edtYear.text}"
        PreferencesProvider.setBirthday(birthDay)
    }
}