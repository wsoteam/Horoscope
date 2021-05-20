package com.wsoteam.horoscopes.presentation.info.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.info_sign_vh.view.*

class InfoSignVH(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup,
    val callbacks: Callbacks
) : RecyclerView.ViewHolder(
    layoutInflater.inflate(
        R.layout.info_sign_vh, viewGroup, false
    )
), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        callbacks.openSign(adapterPosition)
    }

    fun bind(imgId: Int, name: String) {
        itemView.tvSign.text = name
        //itemView.ivSign.setImageResource(imgId)
        Glide.with(itemView.context).asDrawable().load(imgId).into(itemView.ivSign)
    }
}