package com.wsoteam.horoscopes.presentation.onboard.pager.fragments

import android.os.Bundle
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
    }

    fun checkFields(): Boolean {
        return edtDay.text.toString() != "" && edtMonth.text.toString() != "" && edtYear.text.toString() != ""
    }

    fun saveData(){
        var birthDay = "${edtDay.text}.${edtMonth.text}.${edtYear.text}"
        PreferencesProvider.setBirthday(birthDay)
    }
}