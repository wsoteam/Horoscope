package com.wsoteam.horoscopes.presentation.profile

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    companion object{
        private const val SINGLE = 0
        private const val UNSINGLE = 1

        private const val MALE = 0
        private const val FEMALE = 1
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        makeSpan()
        flSingle.setOnClickListener {
            selectRelationship(SINGLE)
        }
        flInLove.setOnClickListener {
            selectRelationship(UNSINGLE)
        }
        flMale.setOnClickListener {
            selectGender(MALE)
        }
        flFemale.setOnClickListener {
            selectGender(FEMALE)
        }
        tvPrivacy.setOnClickListener {

        }
    }

    private fun makeSpan() {
        var span = SpannableString(tvPrivacy.text)
        span.setSpan(UnderlineSpan(), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(UnderlineSpan(), 17, tvPrivacy.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(ForegroundColorSpan(resources.getColor(R.color.privacy_url)), 17, tvPrivacy.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(ForegroundColorSpan(resources.getColor(R.color.privacy_url)), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvPrivacy.text = span
    }

    private fun updateUI() {

    }

    private fun selectRelationship(relationship: Int) {
        if (relationship == SINGLE){
            flSingle.isSelected = true
            flInLove.isSelected = false
        }else{
            flSingle.isSelected = false
            flInLove.isSelected = true
        }
    }


    private fun selectGender(gender: Int) {
        if (gender == MALE){
            flMale.isSelected = true
            flFemale.isSelected = false
        }else{
            flMale.isSelected = false
            flFemale.isSelected = true
        }
    }
}