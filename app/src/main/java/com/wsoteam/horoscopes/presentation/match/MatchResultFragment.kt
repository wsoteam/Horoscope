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
    var ownIndex = -1
    var matchIndex = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchPair = arguments!!.getSerializable(TAG_MATCH_PAIR) as MatchPair
        ownIndex = arguments!!.getInt(TAG_OWN_INDEX)
        matchIndex = arguments!!.getInt(TAG_MATCH_INDEX)

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

        var infoArrayId =
            resources.obtainTypedArray(R.array.info_arrays).getResourceId(ownIndex, -1)
        var percentArrayId =
            resources.obtainTypedArray(R.array.percent_arrays).getResourceId(ownIndex, -1)

        var percent = "${resources.getStringArray(percentArrayId)[matchIndex]}%"
        var info = "${resources.getStringArray(infoArrayId)[matchIndex]}%"

        tvInfo.text = info

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