package com.wsoteam.horoscopes.presentation.onboard.pager.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.choiceSign
import kotlinx.android.synthetic.main.sign_info_fragment.*

class SignInfoFragment : Fragment(R.layout.sign_info_fragment) {

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            var signIndex = choiceSign(PreferencesProvider.getBirthday()!!)

            Log.e("LOL", "sign index --- $signIndex")
            tvTitle.text = "You are ${resources.getStringArray(R.array.names_signs)[signIndex]}"
            tvInfo.text = resources.getStringArray(R.array.sign_info)[signIndex]
            ivSign.setImageResource(resources.obtainTypedArray(R.array.sign_draws).getResourceId(signIndex, 0))
        }
    }
}