package com.wsoteam.horoscopes.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.dialogs.DateDialog
import com.wsoteam.horoscopes.presentation.settings.dialogs.InfoDialog
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ZodiacChoiser
import kotlinx.android.synthetic.main.settings_fragment.*
import java.util.*

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    val INFO_DIALOG = "INFO_DIALOG"
    val DATE_DIALOG = "DATE_DIALOG"

    lateinit var infoDialog: InfoDialog
    lateinit var dateDialog: DateDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindDialogs()
        flDateBirth.setOnClickListener {
            dateDialog.show(activity!!.supportFragmentManager, DATE_DIALOG)
        }
        setDate(PreferencesProvider.getBirthday()!!)
        showPrem()
        swNotif.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                showTimeNotif()
            }else{
                hideTimeNotif()
            }
        }
    }

    private fun bindDialogs() {
        infoDialog = InfoDialog()
        dateDialog = DateDialog()
        dateDialog.setTargetFragment(this, 0)
    }

    fun setDate(birthday: String) {
        val index = ZodiacChoiser.choiceSign(birthday)
        tvDate.text = birthday
        tvSign.text = resources.getStringArray(R.array.names_signs)[index]
        ivSign.setBackgroundResource(resources.obtainTypedArray(R.array.icons_signs).getResourceId(index, -1))
        ivSign.setColorFilter(resources.getColor(R.color.main_violet))
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
            infoDialog.show(childFragmentManager, INFO_DIALOG)
        }
    }

    private fun hidePrem() {
        ivPrem.visibility = View.GONE
        tvPrem.text = getString(R.string.empty_prem)
        llPrem.setOnClickListener {

        }
    }
}