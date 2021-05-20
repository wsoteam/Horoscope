package com.wsoteam.horoscopes.presentation.info.controller

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class InfoSignAdapter(
    val signImgs: ArrayList<Int>,
    val signNames: Array<String>,
    val callbacks: Callbacks
) :
    RecyclerView.Adapter<InfoSignVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoSignVH {
        val inflater = LayoutInflater.from(parent.context)
        return InfoSignVH(inflater, parent, callbacks)
    }

    override fun getItemCount(): Int {
        return signImgs.size
    }

    override fun onBindViewHolder(holder: InfoSignVH, position: Int) {
        holder.bind(signImgs[position], signNames[position])
    }


}