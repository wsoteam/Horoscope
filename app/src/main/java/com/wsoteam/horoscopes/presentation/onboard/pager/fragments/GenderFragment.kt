package com.wsoteam.horoscopes.presentation.onboard.pager.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.gender_fragment.*

class GenderFragment : Fragment(R.layout.gender_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
    }

    private fun updateUI() {
        disableFemaleView()
        enableMaleView()
        setListeners()
    }

    private fun setListeners() {
        llFemale.setOnClickListener {
            if (!ivFemale.isEnabled) {
                invertViews()
            }
        }

        llMale.setOnClickListener {
            if (!ivMale.isEnabled) {
                invertViews()
            }
        }
    }

    private fun invertViews() {
        ivMale.isEnabled = !ivMale.isEnabled
        tvMale.isEnabled = !tvMale.isEnabled

        ivFemale.isEnabled = !ivFemale.isEnabled
        tvFemale.isEnabled = !tvFemale.isEnabled
    }

    private fun enableMaleView() {
        ivMale.isEnabled = true
        tvMale.isEnabled = true
    }

    private fun disableFemaleView() {
        ivFemale.isEnabled = false
        tvFemale.isEnabled = false
    }

    fun saveData() {
        var genderId = PreferencesProvider.FEMALE
        if (ivMale.isEnabled) {
            genderId = PreferencesProvider.MALE
        }
        PreferencesProvider.userGender = genderId
    }
}