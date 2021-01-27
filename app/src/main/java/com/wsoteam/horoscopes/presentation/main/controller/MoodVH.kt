package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.mood_vh.view.*

class MoodVH(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup,
    val iGetPrem: IGetPrem
) :
    RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.mood_vh, viewGroup, false)) {
    fun bind(
        sex: Int,
        hustle: Int,
        vibe: Int,
        success: Int,
        isLocked: Boolean
    ) {
        if (isLocked) {
            itemView.ivBlur.visibility = View.VISIBLE
            //Glide.with(itemView.context).load(R.drawable.blur).into(itemView.ivBlur)
            itemView.llLock.visibility = View.VISIBLE
            itemView.btnLockPrem.setOnClickListener { iGetPrem.getPrem() }
        }else{
            itemView.ivBlur.visibility = View.GONE
            itemView.llLock.visibility = View.GONE
        }
        
        if (PreferencesProvider.isNeedNewTheme){
            itemView.tvLabelSex.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))
            itemView.tvLabelSuccess.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))
            itemView.tvLabelVibe.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))
            itemView.tvLabelHustle.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))


            itemView.tvLabelSex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sex_white, 0, 0, 0)
            itemView.tvLabelSuccess.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_success_white, 0, 0, 0)
            itemView.tvLabelVibe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vibe_white, 0, 0, 0)
            itemView.tvLabelHustle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hustle_white, 0, 0, 0)

            itemView.rvSex.setWhiteTheme()
            itemView.rvHustle.setWhiteTheme()
            itemView.rvVibe.setWhiteTheme()
            itemView.rvSuccess.setWhiteTheme()
        }

        itemView.rvSex.setRating(sex)
        itemView.rvHustle.setRating(hustle)
        itemView.rvVibe.setRating(vibe)
        itemView.rvSuccess.setRating(success)
    }
}
