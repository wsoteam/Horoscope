package com.wsoteam.horoscopes.presentation.onboard.pager.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.HostActivity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.host_activity.*
import kotlinx.android.synthetic.main.name_fragment.*

class NameFragment : Fragment(R.layout.name_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty() && s.toString() != " ") {
                    enableNexButton()
                }else{
                    disableNextButton()
                }
            }
        })
    }

    fun checkFields(): Boolean {
        return edtName.text.toString() != "" && edtName.text.toString() != " "
    }

    fun saveData() {
        PreferencesProvider.setName(edtName.text.toString())
    }

    private fun disableNextButton() {
        (activity as HostActivity).btnStart.isEnabled = false
    }

    private fun enableNexButton() {
        (activity as HostActivity).btnStart.isEnabled = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (checkFields()) {
                enableNexButton()
            } else {
                disableNextButton()
            }
        }
    }


}