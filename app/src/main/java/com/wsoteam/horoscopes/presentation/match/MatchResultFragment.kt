package com.wsoteam.horoscopes.presentation.match

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R

class MatchResultFragment : Fragment(R.layout.match_result_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        const val TAG_OWN_SIGN_IMG_ID = "TAG_OWN_SIGN_IMG_ID"
        const val TAG_MATCH_SIGN_IMG_ID = "TAG_MATCH_SIGN_IMG_ID"

        fun newInstance(ownSignImgId: Int, matchSignImgId: Int): MatchResultFragment {
            var args = Bundle().apply {
                putInt(TAG_OWN_SIGN_IMG_ID, ownSignImgId)
                putInt(TAG_MATCH_SIGN_IMG_ID, matchSignImgId)
            }
            return MatchResultFragment().apply {
                arguments = args
            }
        }
    }
}