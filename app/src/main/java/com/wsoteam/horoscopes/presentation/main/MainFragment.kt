package com.wsoteam.horoscopes.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.main.controller.HoroscopeAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.match_vh.view.*

class MainFragment : Fragment(R.layout.main_fragment) {

    lateinit var vm: MainVM
    var index = -1

    companion object{

        val INDEX_KEY = "INDEX_KEY"

        fun newInstance(index : Int): MainFragment {
            var bundle = Bundle()
            bundle.putInt(INDEX_KEY, index)
            var fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index = arguments!!.getInt(INDEX_KEY)
        ivMain.setBackgroundResource(
            resources.obtainTypedArray(R.array.imgs_signs)
                .getResourceId(index, -1)
        )
        vm = ViewModelProviders.of(activity!!).get(MainVM::class.java)

        rvMain.layoutManager = LinearLayoutManager(this.context)
        rvMain.adapter = HoroscopeAdapter()
    }
}