package com.wsoteam.horoscopes.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.wsoteam.horoscopes.R

class RatingView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {


    private val layout = R.layout.rating_view

    private val firstStar: ImageView
    private val secondStar: ImageView
    private val thirdStar: ImageView
    private val firthStar: ImageView
    private val fifthStar: ImageView

    private var listStars: MutableList<ImageView>

    //attr
    private val attrRating: Int
    private var isWhiteTheme: Boolean

    init {
        inflate(context, layout, this)

        firstStar = findViewById(R.id.ivFirst)
        secondStar = findViewById(R.id.ivSecond)
        thirdStar = findViewById(R.id.ivThird)
        firthStar = findViewById(R.id.ivFirth)
        fifthStar = findViewById(R.id.ivFifth)

        listStars = mutableListOf(firstStar, secondStar, thirdStar, firthStar, fifthStar)

        context.theme.obtainStyledAttributes(attrs, R.styleable.RatingView, 0, 0)
            .apply {
                try {
                    attrRating = getResourceId(R.styleable.RatingView_rating, 0)
                    isWhiteTheme = getBoolean(R.styleable.RatingView_isWhiteTheme, false)
                } finally {
                    recycle()
                }
            }

    }

    fun setWhiteTheme(){
        isWhiteTheme = true
    }

    fun setRating(rating : Int){
        if (isWhiteTheme){
            firstStar.setImageResource(R.drawable.ic_star_empty_white)
            secondStar.setImageResource(R.drawable.ic_star_empty_white)
            thirdStar.setImageResource(R.drawable.ic_star_empty_white)
            firthStar.setImageResource(R.drawable.ic_star_empty_white)
            fifthStar.setImageResource(R.drawable.ic_star_empty_white)

            for (i in 0 until rating){
                listStars[i].setImageResource(R.drawable.ic_star_full_white)
            }
        }else{
            for (i in 0 until rating){
                listStars[i].setImageResource(R.drawable.ic_star_full)
            }
        }
    }

}