package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.match_vh.view.*

class MatchVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(
    layoutInflater.inflate(
        R.layout.match_vh, viewGroup, false
    )
) {
    fun bind(loveId: Int, friendId: Int, careerId: Int) {


        itemView.tvCareer.text =
            itemView.resources.getStringArray(R.array.names_signs)[careerId - 1]
        itemView.tvFriend.text =
            itemView.resources.getStringArray(R.array.names_signs)[friendId - 1]
        itemView.tvLove.text = itemView.resources.getStringArray(R.array.names_signs)[loveId - 1]

        if (PreferencesProvider.isNeedNewTheme){
            itemView.tvTitle.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))

            itemView.tvLoveLabel.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))
            itemView.tvLove.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))

            itemView.tvFriendLabel.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))
            itemView.tvFriend.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))

            itemView.tvCareerLabel.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))
            itemView.tvCareer.setTextColor(itemView.resources.getColor(R.color.white_theme_text_color))

            itemView.dvdMatch.setBackgroundColor(itemView.resources.getColor(R.color.white_divider))

            itemView.ivLove.setImageResource(
                itemView.resources.obtainTypedArray(R.array.imgs_signs_matches_white)
                    .getResourceId(loveId - 1, -1)
            )
            itemView.ivCareer.setImageResource(
                itemView.resources.obtainTypedArray(R.array.imgs_signs_matches_white)
                    .getResourceId(careerId - 1, -1)
            )
            itemView.ivFriend.setImageResource(
                itemView.resources.obtainTypedArray(R.array.imgs_signs_matches_white)
                    .getResourceId(friendId - 1, -1)
            )
        }else{
            itemView.ivLove.setImageResource(
                itemView.resources.obtainTypedArray(R.array.imgs_signs_matches)
                    .getResourceId(loveId - 1, -1)
            )
            itemView.ivCareer.setImageResource(
                itemView.resources.obtainTypedArray(R.array.imgs_signs_matches)
                    .getResourceId(careerId - 1, -1)
            )
            itemView.ivFriend.setImageResource(
                itemView.resources.obtainTypedArray(R.array.imgs_signs_matches)
                    .getResourceId(friendId - 1, -1)
            )
        }
    }
}