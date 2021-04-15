package com.wsoteam.horoscopes.presentation.match.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.item_match_sign.view.*

class MatchSignVH(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup,
    val iClick: IClick
) :
    RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_match_sign, viewGroup, false)),
    View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        iClick.onClick(adapterPosition)
    }

    fun bind(resourceId: Int, name: String, isEnabled: Boolean) {
        itemView.tvSign.setCompoundDrawablesWithIntrinsicBounds(resourceId, 0, 0, 0)
        itemView.tvSign.text = name
        itemView.tvSign.isEnabled = isEnabled
    }
}