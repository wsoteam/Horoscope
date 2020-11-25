package com.wsoteam.horoscopes.presentation.crystals.charge

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.Config
import com.wsoteam.horoscopes.presentation.crystals.charge.dialogs.ChargeInterruptDialog
import com.wsoteam.horoscopes.presentation.crystals.charge.dialogs.ChargeSuccessDialog
import com.wsoteam.horoscopes.presentation.crystals.premium.CrystalPremiumActivity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.charge_activity.*

class ChargeActivity : AppCompatActivity(R.layout.charge_activity) {

    var typeImgId = -1
    var crystalImgId = -1

    var affirsId = -1
    lateinit var affirs: Array<String>

    var typeName = ""

    var counter = 31
    lateinit var countDownTimer: CountDownTimer

    var player: MediaPlayer? = null
    var isVolumeOn = true

    lateinit var successDialog: ChargeSuccessDialog
    lateinit var interruptDialog: ChargeInterruptDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        successDialog = ChargeSuccessDialog()
        interruptDialog = ChargeInterruptDialog()

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
                    counter--
                    tvCountdownCharge.text = "00 : ${"%02d".format(counter)}"
                    if (counter % 7 == 0 && counter != 0) {
                        tvAffir.text = affirs[(counter / 7) - 1]
                    }
                } else {
                    countDownTimer.cancel()
                    closeCharge()
                }
            }
        }

        ivMusicState.setOnClickListener {
            if (isVolumeOn) {
                player!!.pause()
                ivMusicState.setImageResource(R.drawable.ic_music_on)
                isVolumeOn = false
            } else {
                player!!.start()
                ivMusicState.setImageResource(R.drawable.ic_music_off)
                isVolumeOn = true
            }
        }

        tvStop.setOnClickListener {
            interruptDialog.show(supportFragmentManager, "")
        }
    }

    override fun onBackPressed() {
        interruptDialog.show(supportFragmentManager, "")
        //super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        resumeCharge()
    }

    override fun onPause() {
        super.onPause()
        pauseCharge()
    }

    fun pauseCharge() {
        if (player!!.isPlaying) {
            player!!.pause()
        }
        countDownTimer.cancel()
    }

    fun resumeCharge() {
        if (isVolumeOn) {
            setupAndPlay()
        }
        countDownTimer.start()
    }

    private fun closeCharge() {
        successDialog.show(supportFragmentManager, "")
    }

    private fun setupAndPlay() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.meditation)
            player!!.isLooping = true
            player!!.start()
        }else{
            player!!.start()
        }
    }

    fun closeActivity() {
        if (PreferencesProvider.isADEnabled()) {
            startActivity(Intent(this, CrystalPremiumActivity::class.java))
        }
        finish()
    }

}