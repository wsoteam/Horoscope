package com.wsoteam.horoscopes.presentation.onboard.pager.fragments

import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.name_fragment.*

class NameFragment : Fragment(R.layout.name_fragment) {


    fun checkFields(): Boolean {
        return edtName.text.toString() != "" && edtName.text.toString() != " "
    }

    fun saveData(){
        PreferencesProvider.setName(edtName.text.toString())
    }
}