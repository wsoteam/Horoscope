package com.wsoteam.horoscopes.presentation.profile

import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.profile.dialogs.DateFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.form_activity.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.edtName
import java.util.*

class ProfileFragment : Fragment(R.layout.profile_fragment), DateFragment.Callbacks {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        makeSpan()
        setListeners()

    }

    private fun setListeners() {
        flSingle.setOnClickListener {
            selectRelationship(PreferencesProvider.SINGLE)
        }
        flInLove.setOnClickListener {
            selectRelationship(PreferencesProvider.UNSINGLE)
        }
        flMale.setOnClickListener {
            selectGender(PreferencesProvider.MALE)
        }
        flFemale.setOnClickListener {
            selectGender(PreferencesProvider.FEMALE)
        }
        tvPrivacy.setOnClickListener {

        }

        edtName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                PreferencesProvider.setName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edtPlace.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                PreferencesProvider.userPlace = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edtBirth.setOnClickListener {
            DateFragment
                .newInstance(getDay(), getMonth(), getYear()).apply {
                    setTargetFragment(this@ProfileFragment, -1)
                    show(this@ProfileFragment.requireFragmentManager(), "")
                }

        }
    }

    private fun getYear(): Int {
        return PreferencesProvider.getBirthday()!!.split(".")[2].toInt()
    }

    private fun getMonth(): Int {
        return PreferencesProvider.getBirthday()!!.split(".")[1].toInt()
    }

    private fun getDay(): Int {
        return PreferencesProvider.getBirthday()!!.split(".")[0].toInt()
    }


    private fun makeSpan() {
        var span = SpannableString(tvPrivacy.text)
        span.setSpan(UnderlineSpan(), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(UnderlineSpan(), 17, tvPrivacy.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.privacy_url)),
            17,
            tvPrivacy.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.privacy_url)),
            0,
            12,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvPrivacy.text = span
    }

    private fun updateUI() {
        edtName.setText(PreferencesProvider.getName())
        edtBirth.setText(PreferencesProvider.getBirthday())
        edtTimeBirth.setText(PreferencesProvider.birthTime)
        edtPlace.setText(PreferencesProvider.userPlace)
        selectGender(PreferencesProvider.userGender)
        selectRelationship(PreferencesProvider.userRelationship)
    }

    private fun selectRelationship(relationship: Int) {
        if (relationship == PreferencesProvider.SINGLE) {
            flSingle.isSelected = true
            flInLove.isSelected = false
            PreferencesProvider.userRelationship = PreferencesProvider.SINGLE
        } else {
            flSingle.isSelected = false
            flInLove.isSelected = true
            PreferencesProvider.userRelationship = PreferencesProvider.UNSINGLE
        }
    }


    private fun selectGender(gender: Int) {
        if (gender == PreferencesProvider.MALE) {
            flMale.isSelected = true
            flFemale.isSelected = false
            PreferencesProvider.userGender = PreferencesProvider.MALE
        } else {
            flMale.isSelected = false
            flFemale.isSelected = true
            PreferencesProvider.userGender = PreferencesProvider.FEMALE
        }
    }

    override fun setNewBirthday(date: String) {
        edtBirth.setText(date)
        PreferencesProvider.setBirthday(date)
    }
}