package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HoroscopeAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TEXT_TYPE = 0
    val MATCH_TYPE = 1
    val MOOD_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TEXT_TYPE -> TextVH(LayoutInflater.from(parent.context), parent)
            MATCH_TYPE -> MatchVH(LayoutInflater.from(parent.context), parent)
            else -> TextVH(LayoutInflater.from(parent.context), parent)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TEXT_TYPE -> (holder as TextVH).bind("Jul 12, 2020 - The possibility of some future houseguests might have you checking your home to see what needs to be done to make it presentable. It may need a few minor repairs, Aries, and you could do some online shopping to dress the place up a little. Books might give you some workable ideas. Your mind might be working more quickly than your body, however. Take care not to push yourself too hard.")
            MATCH_TYPE -> (holder as MatchVH).bind(0, 1, 3)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}