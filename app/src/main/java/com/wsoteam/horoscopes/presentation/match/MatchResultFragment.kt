package com.wsoteam.horoscopes.presentation.match

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.gms.ads.AdRequest
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.MatchPair.MatchPair
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.BannerFrequency
import kotlinx.android.synthetic.main.description_fragment.*
import kotlinx.android.synthetic.main.match_result_fragment.*
import kotlinx.android.synthetic.main.match_result_fragment.adView

class MatchResultFragment : Fragment(R.layout.match_result_fragment) {

    private lateinit var matchPair: MatchPair
    private var ownIndex = -1
    private var matchIndex = -1

    private lateinit var animPercent: ValueAnimator
    private lateinit var animProgressBar: ValueAnimator
    private lateinit var animXTranslationLeft: ValueAnimator
    private lateinit var animXTranslationRight: ValueAnimator

    private var leftEndTrans = 5.0f
    private var rightEndTrans = -5.0f

    private var animDuration = 1_500L

    private var percent = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchPair = arguments!!.getSerializable(TAG_MATCH_PAIR) as MatchPair
        ownIndex = arguments!!.getInt(TAG_OWN_INDEX)
        matchIndex = arguments!!.getInt(TAG_MATCH_INDEX)
        setSignsImages()
        setTexts()
        setupAnimations()
        playAnimations()

        ivClose.setOnClickListener {
            requireActivity().onBackPressed()
        }

        if (PreferencesProvider.isADEnabled() && BannerFrequency.needShow() && !Config.FOR_TEST) {
            adView.visibility = View.VISIBLE
            adView.loadAd(AdRequest.Builder().build())
        }
    }

    private fun setTexts() {
        var infoArrayId =
            resources.obtainTypedArray(R.array.info_arrays).getResourceId(ownIndex, -1)
        var percentArrayId =
            resources.obtainTypedArray(R.array.percent_arrays).getResourceId(ownIndex, -1)

        percent = resources.getIntArray(percentArrayId)[matchIndex]
        var info = resources.getStringArray(infoArrayId)[matchIndex]

        tvInfo.text = info
        tvMatchPair.text = "${matchPair.ownSignName} + ${matchPair.matchSignName}"
    }

    private fun setSignsImages() {
        var ownSignDraw =
            VectorDrawableCompat.create(resources, matchPair.ownImgId, null) as Drawable
        ownSignDraw = DrawableCompat.wrap(ownSignDraw)
        DrawableCompat.setTint(ownSignDraw, resources.getColor(R.color.match_result_signs))

        var matchSignDraw =
            VectorDrawableCompat.create(resources, matchPair.matchImgId, null) as Drawable
        matchSignDraw = DrawableCompat.wrap(matchSignDraw)
        DrawableCompat.setTint(matchSignDraw, resources.getColor(R.color.match_result_signs))

        ivMatchSign.setImageDrawable(matchSignDraw)
        ivOwnSign.setImageDrawable(ownSignDraw)
    }

    private fun playAnimations() {
        animPercent.start()
        animProgressBar.start()
        animXTranslationLeft.start()
        animXTranslationRight.start()
    }

    private fun setupAnimations() {
        animPercent = ValueAnimator.ofInt(0, percent)
        animPercent.duration = animDuration
        animPercent.addUpdateListener {
            tvProgress.text = "${it.animatedValue}%"
        }

        animProgressBar = ValueAnimator.ofInt(0, percent)
        animProgressBar.duration = animDuration
        animProgressBar.addUpdateListener {
            pbMatch.progress = it.animatedValue.toString().toInt()
        }

        animXTranslationLeft = ValueAnimator.ofFloat(ivOwnSign.translationX, leftEndTrans)
        animXTranslationLeft.duration = animDuration
        animXTranslationLeft.addUpdateListener {
            ivOwnSign.translationX = it.animatedValue.toString().toFloat()
        }

        animXTranslationRight = ValueAnimator.ofFloat(ivMatchSign.translationX, rightEndTrans)
        animXTranslationRight.duration = animDuration
        animXTranslationRight.addUpdateListener {
            ivMatchSign.translationX = it.animatedValue.toString().toFloat()
        }
    }

    companion object {

        const val TAG_MATCH_PAIR = "TAG_MATCH_PAIR"
        const val TAG_OWN_INDEX = "TAG_OWN_INDEX"
        const val TAG_MATCH_INDEX = "TAG_MATCH_INDEX"

        fun newInstance(matchPair: MatchPair, ownIndex: Int, matchIndex: Int): MatchResultFragment {
            var args = Bundle().apply {
                putSerializable(TAG_MATCH_PAIR, matchPair)
                putInt(TAG_OWN_INDEX, ownIndex)
                putInt(TAG_MATCH_INDEX, matchIndex)
            }
            return MatchResultFragment().apply {
                arguments = args
            }
        }
    }
}