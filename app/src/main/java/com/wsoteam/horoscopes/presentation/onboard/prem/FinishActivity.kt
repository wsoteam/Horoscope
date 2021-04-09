package com.wsoteam.horoscopes.presentation.onboard.prem

import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.finish_prem_activity.*

class FinishActivity : AppCompatActivity(R.layout.finish_prem_activity) {

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        var timer = object : CountDownTimer(10000, 1000){
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                counter++
            }
        }

        timer.start()
    }


}