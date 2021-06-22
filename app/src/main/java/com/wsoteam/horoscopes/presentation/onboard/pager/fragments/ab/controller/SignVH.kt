package com.wsoteam.horoscopes.presentation.onboard.pager.fragments.ab.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.sign_vh.view.*

class SignVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, var iChoiseSignCallbacks : ChoiseSignAdapter.IChoiseSignCallbacks) : RecyclerView.ViewHolder(
    layoutInflater.inflate(
        R.layout.sign_vh, viewGroup, false
    )), View.OnClickListener
 {
    init {
        itemView.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        iChoiseSignCallbacks?.onClick(adapterPosition)
    }

    fun bind(img: Int, name: String, isSelected : Boolean) {
        itemView.tvSign.text = name
        itemView.ivSign.setImageResource(img)

        if(isSelected){
            itemView.ivSelector.visibility = View.VISIBLE
        }else{
            itemView.ivSelector.visibility = View.INVISIBLE
        }

    }
}