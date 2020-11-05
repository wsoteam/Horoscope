package com.wsoteam.horoscopes.presentation.crystals.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TopProgressAdapter() : RecyclerView.Adapter<TopProgressVH>() {

    val COUNT = 5
    val statesArray = arrayListOf<Int>(ProgressConfig.EMPTY, ProgressConfig.EMPTY, ProgressConfig.EMPTY, ProgressConfig.EMPTY, ProgressConfig.EMPTY)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProgressVH {
        return TopProgressVH(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return COUNT
    }

    override fun onBindViewHolder(holder: TopProgressVH, position: Int) {
        holder.bind(statesArray[position], object : ICounter{
            override fun endCount() {
                statesArray[position] = ProgressConfig.FIN
                if (position < COUNT) {
                    if (position < COUNT - 1) {
                        statesArray[position + 1] = ProgressConfig.NEED_START
                    }
                    notifyDataSetChanged()
                }else{
                    finishLoad()
                }
            }
        })
    }

    private fun finishLoad() {

    }

    fun start(){
        statesArray[0] = ProgressConfig.NEED_START
        notifyDataSetChanged()

    }
}