package com.wsoteam.horoscopes.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.settings.dialogs.InfoDialog
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate(PreferencesProvider.getBirthday()!!, 0)
        showPrem()
        swNotif.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                showTimeNotif()
            }else{
                hideTimeNotif()
            }
        }
    }

    private fun setDate(birthday: String, signIndex: Int) {
        tvDate.text = birthday
    }

    private fun showTimeNotif() {
        llNotifTime.visibility = View.VISIBLE
        llNotifTime.setOnClickListener {

        }
    }

    private fun hideTimeNotif() {
        llNotifTime.visibility = View.GONE
    }

    private fun showPrem() {
        ivPrem.visibility = View.VISIBLE
        tvPrem.text = getString(R.string.prem_set)
        llPrem.setOnClickListener {
            InfoDialog().show(childFragmentManager, "InfoDialog")
        }
    }

    private fun hidePrem() {
        ivPrem.visibility = View.GONE
        tvPrem.text = getString(R.string.empty_prem)
        llPrem.setOnClickListener {

        }
    }
}