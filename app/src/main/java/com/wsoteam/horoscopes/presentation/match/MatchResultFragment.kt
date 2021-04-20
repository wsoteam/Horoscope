package com.wsoteam.horoscopes.presentation.match

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.MatchPair.MatchPair
import kotlinx.android.synthetic.main.match_result_fragment.*

class MatchResultFragment : Fragment(R.layout.match_result_fragment) {

    lateinit var matchPair: MatchPair

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchPair = arguments!!.getSerializable(TAG_MATCH_PAIR) as MatchPair

        var ownSignDraw = VectorDrawableCompat.create(resources, matchPair.ownImgId, null) as Drawable
        ownSignDraw = DrawableCompat.wrap(ownSignDraw)
        DrawableCompat.setTint(ownSignDraw, resources.getColor(R.color.match_result_signs))

        var matchSignDraw = VectorDrawableCompat.create(resources, matchPair.matchImgId, null) as Drawable
        matchSignDraw = DrawableCompat.wrap(matchSignDraw)
        DrawableCompat.setTint(matchSignDraw, resources.getColor(R.color.match_result_signs))

        ivMatchSign.setImageDrawable(matchSignDraw)
        ivOwnSign.setImageDrawable(ownSignDraw)

    }

    companion object {

        const val TAG_MATCH_PAIR = "TAG_MATCH_PAIR"

        fun newInstance(matchPair: MatchPair): MatchResultFragment {
            var args = Bundle().apply {
                putSerializable(TAG_MATCH_PAIR, matchPair)
            }
            return MatchResultFragment().apply {
                arguments = args
            }
        }
    }
}