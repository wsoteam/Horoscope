package com.wsoteam.horoscopes.presentation.crystals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.controller.TopProgressAdapter
import kotlinx.android.synthetic.main.stories_onboard_activity.*

class StoriesOnboardActivity : AppCompatActivity(R.layout.stories_onboard_activity) {

    var topProgressAdapter : TopProgressAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rvTopProgress.layoutManager = GridLayoutManager(this, 5)
        topProgressAdapter = TopProgressAdapter()
        rvTopProgress.adapter = topProgressAdapter
        topProgressAdapter!!.start()
    }
}