package com.wsoteam.horoscopes.presentation.crystals.charge

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.Config
import kotlinx.android.synthetic.main.charge_activity.*

class ChargeActivity : AppCompatActivity(R.layout.charge_activity) {

    var typeImgId = -1
    var crystalImgId = -1

    var affirsId = -1
    lateinit var affirs: Array<String>

    var typeName = ""

    var counter = 31
    lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        typeImgId = intent.getIntExtra(Config.TAG_TYPE_IMG_ID, -1)
        crystalImgId = intent.getIntExtra(Config.TAG_CRYSTAL_IMG_ID, -1)

        typeName = intent.getStringExtra(Config.TAG_TYPE_NAME)

        affirsId = intent.getIntExtra(Config.TAG_AFFIR_LIST, -1)
        affirs = resources.getStringArray(affirsId)
        affirs.reverse()
        tvAffir.text = affirs[affirs.size - 1]

        ivTypeCharge.setImageResource(typeImgId)
        tvNameTypeCharge.text = typeName

        ivMainCharge.setImageResource(crystalImgId)

        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                if (counter > 0) {
                    counter --
                    tvCountdownCharge.text = "00 : ${"%02d".format(counter)}"
                    if (counter % 7 == 0 && counter != 0){
                        tvAffir.text = affirs[(counter / 7) - 1]
                    }
                } else {
                    countDownTimer.cancel()
                    closeCharge()
                }
            }
        }
        countDownTimer.start()

    }

    private fun closeCharge() {
        Log.e("LOL", "fin")
    }

}