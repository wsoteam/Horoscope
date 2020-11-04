package com.wsoteam.horoscopes.presentation.crystals.controller

import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.item_top_progress.view.*

class TopProgressVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) :
    RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_top_progress, viewGroup, false)) {

    var iCounter: ICounter? = null
    var timer: CountDownTimer? = null

    fun bind(state: Int, iCounter: ICounter) {
        this.iCounter = iCounter
        when (state) {
            ProgressConfig.EMPTY -> clearProgresBar()
            ProgressConfig.FIN -> finProgressBar()
            ProgressConfig.NEED_START -> startCountDown()
        }
    }

    private fun startCountDown() {
        var currentProgress = 0
        timer = object : CountDownTimer(ProgressConfig.delay, ProgressConfig.interval) {
            override fun onFinish() {
                itemView.pbTopProgress.progress = 1000
                iCounter!!.endCount()
                timer!!.cancel()
            }

            override fun onTick(millisUntilFinished: Long) {
                if (currentProgress < 1001) {
                    itemView.pbTopProgress.progress = currentProgress
                    currentProgress++
                }else{
                    Log.e("LOL", "fin")
                    timer!!.onFinish()
                }
            }
        }
        timer!!.start()
    }

    private fun finProgressBar() {
        itemView.pbTopProgress.progress = 1000
    }

    private fun clearProgresBar() {
        itemView.pbTopProgress.progress = 0
    }


}