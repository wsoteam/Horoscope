package com.wsoteam.horoscopes.presentation.match

import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.match.controller.IClick
import com.wsoteam.horoscopes.presentation.match.controller.MatchSignsAdapter
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.getSignIndexShuffleArray
import kotlinx.android.synthetic.main.match_fragment.*

class MatchFragment : Fragment(R.layout.match_fragment) {

    lateinit var imgsArray: TypedArray
    lateinit var signsNames: Array<String>
    lateinit var rvAdapter: MatchSignsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgsArray = resources.obtainTypedArray(R.array.match_signs_imgs)
        signsNames = resources.getStringArray(R.array.match_signs_names)

        btnShow.isEnabled = false
        setOwnSign()

        rvAdapter = MatchSignsAdapter(imgsArray, signsNames, object : IClick {
            override fun onClick(position: Int) {
                setMatchSign(position)

            }
        })

        rvSigns.layoutManager = GridLayoutManager(activity, 2)
        rvSigns.adapter = rvAdapter

    }

    private fun setMatchSign(position: Int) {
        tvMatchSign.text = signsNames[position]
        tvMatchSign.setTextColor(resources.getColor(R.color.white))
        ivMatchSign.setImageResource(imgsArray.getResourceId(position, -1))
        btnShow.isEnabled = true
    }

    private fun setOwnSign() {
        ivOwnSign.isEnabled = false
        var index = getSignIndexShuffleArray(PreferencesProvider.getBirthday()!!)
        ivOwnSign.setImageResource(imgsArray.getResourceId(index, -1))
        tvOwnSign.text = signsNames[index]
    }
}