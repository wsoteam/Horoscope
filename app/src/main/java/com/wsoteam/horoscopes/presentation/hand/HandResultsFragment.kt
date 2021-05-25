package com.wsoteam.horoscopes.presentation.hand

import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.hand.controllers.HandResultAdapter
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.hand_results_fragment.*
import kotlinx.android.synthetic.main.info_fragment.*

class HandResultsFragment : Fragment(R.layout.hand_results_fragment) {

    private var index = -1

    lateinit var listTitles: Array<String>
    lateinit var listHandsImages: TypedArray
    lateinit var colors: TypedArray
    lateinit var progressShapes: TypedArray
    lateinit var percents: IntArray
    lateinit var texts: Array<String>

    lateinit var adapter: HandResultAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index = PreferencesProvider.handInfoIndex

        listTitles = resources.getStringArray(R.array.hands_titles)
        listHandsImages = resources.obtainTypedArray(R.array.hands_imgs)
        colors = resources.obtainTypedArray(R.array.colors)
        progressShapes = resources.obtainTypedArray(R.array.progress_shapes)


        var idPercentsList =
            resources.obtainTypedArray(R.array.hand_percents).getResourceId(index, -1)
        percents = resources.getIntArray(idPercentsList)

        var idTextsList =
            resources.obtainTypedArray(R.array.hands_texts).getResourceId(index, -1)
        texts = resources.getStringArray(idTextsList)

        adapter = HandResultAdapter(listTitles, listHandsImages, colors, progressShapes, percents, texts)
        rvResults.adapter = adapter
        rvResults.layoutManager = LinearLayoutManager(requireContext())


    }
}