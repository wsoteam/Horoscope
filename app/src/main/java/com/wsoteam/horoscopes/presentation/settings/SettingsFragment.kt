package com.wsoteam.horoscopes.presentation.settings

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.notification.AlarmReceiver
import com.wsoteam.horoscopes.presentation.form.dialogs.DateDialog
import com.wsoteam.horoscopes.presentation.settings.dialogs.InfoDialog
import com.wsoteam.horoscopes.presentation.settings.dialogs.TimeDialog
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.choiceSign
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : Fragment(R.layout.settings_fragment) {

    val INFO_DIALOG = "INFO_DIALOG"
    val DATE_DIALOG = "DATE_DIALOG"
    val TIME_DIALOG = "TIME_DIALOG"

    lateinit var infoDialog: InfoDialog
    lateinit var timeDialog: TimeDialog
    lateinit var dateDialog: DateDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindDialogs()
        bindSwitch()
        setDate(PreferencesProvider.getBirthday()!!)
        showPrem()
        flDateBirth.setOnClickListener {
            dateDialog.show(activity!!.supportFragmentManager, DATE_DIALOG)
        }
        swNotif.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                showTimeNotif()
            }else{
                hideTimeNotif()
            }
        }
    }

    private fun bindSwitch() {
        if (PreferencesProvider.getNotifStatus()!!){
            swNotif.isChecked = true
            showTimeNotif()
        } else{
            swNotif.isChecked = false
            hideTimeNotif()
        }
    }

    private fun bindDialogs() {
        infoDialog = InfoDialog()
        dateDialog = DateDialog()
        dateDialog.setTargetFragment(this, 0)

        timeDialog = TimeDialog()
        timeDialog.setTargetFragment(this, 0)
    }

    fun setTime(time : String){
        tvTime.text = time
    }

    fun setDate(birthday: String) {
        val index = choiceSign(birthday)
        tvDate.text = birthday
        tvSign.text = resources.getStringArray(R.array.names_signs)[index]
        ivSign.setBackgroundResource(resources.obtainTypedArray(R.array.icons_signs).getResourceId(index, -1))
        ivSign.setColorFilter(resources.getColor(R.color.main_violet))
    }

    private fun showTimeNotif() {
        PreferencesProvider.setNotifStatus(true)
        tvTime.text = PreferencesProvider.getNotifTime()
        llNotifTime.visibility = View.VISIBLE
        llNotifTime.setOnClickListener {
            timeDialog.show(activity!!.supportFragmentManager, TIME_DIALOG)
        }
        AlarmReceiver.startNotification(context)
    }

    private fun hideTimeNotif() {
        llNotifTime.visibility = View.GONE
        PreferencesProvider.setNotifStatus(false)
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