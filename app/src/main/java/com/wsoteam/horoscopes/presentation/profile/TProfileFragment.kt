package com.wsoteam.horoscopes.presentation.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.profile.dialogs.ChoiceSignDialog
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.choiceSign
import com.wsoteam.horoscopes.utils.getDateFromSign
import com.wsoteam.horoscopes.utils.getSignIndexShuffleArray
import kotlinx.android.synthetic.main.tprofile_fragment.*


class TProfileFragment : Fragment(R.layout.tprofile_fragment), ChoiceSignDialog.Callbacks {


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
            var intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://cloud.mail.ru/public/3DGd/aR8UVay3N")
            startActivity(intent)
        }

        btnChoiceSign.setOnClickListener {
            ChoiceSignDialog
                .newInstance(choiceSign(PreferencesProvider.getBirthday()!!)).apply {
                    setTargetFragment(this@TProfileFragment, -1)
                    show(this@TProfileFragment.requireFragmentManager(), "")
                }
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
    }

    private fun updateUI() {
        edtName.setText(PreferencesProvider.getName())
        selectGender(PreferencesProvider.userGender)
        selectRelationship(PreferencesProvider.userRelationship)
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

    override fun setNewSign(index: Int) {
        PreferencesProvider.setBirthday(getDateFromSign(index))
        (requireActivity() as ProfileFragment.Callbacks).refreshSing()
    }

}