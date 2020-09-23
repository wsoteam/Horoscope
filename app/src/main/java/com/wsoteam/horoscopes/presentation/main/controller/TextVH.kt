package com.wsoteam.horoscopes.presentation.main.controller

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.MaskFilterSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.text_vh.view.*

class TextVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(
    R.layout.text_vh, viewGroup, false)) {

    fun bind(text: String, isLocked: Boolean) {
        if (isLocked) {
            var openText = createOpenText(text)
            var wholeText = openText + "\n\n" + text.substring(200)
            var spannable = SpannableString(wholeText)
            var filter = BlurMaskFilter(12f, BlurMaskFilter.Blur.NORMAL)
            spannable.setSpan(MaskFilterSpan(filter), openText.length + 2, spannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) //random
            itemView.tvText.text = spannable
        }else{
            itemView.tvText.text = text
        }
    }

    private fun createOpenText(text: String): String{
        var words = text.split(" ")
        var text = ""
        for (i in 0..50){
            if (i == 50 && words[i].contains(",")){
                break
            }
            if (i != 0){
                text += " "
            }
            text += words[i]
        }
        text += "..."
        return text
    }


}