package com.wsoteam.horoscopes.presentation.main.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.main.MainFragment
import com.wsoteam.horoscopes.presentation.main.controller.HoroscopeAdapter
import kotlinx.android.synthetic.main.page_fragment.*
import java.io.Serializable

class PageFragment : Fragment(R.layout.page_fragment) {

    companion object {

        val DATA_KEY = "DATA_KEY"

        fun newInstance(index: Int, sign: Serializable): MainFragment {
            var bundle = Bundle()
            bundle.putSerializable(DATA_KEY, sign)
            var fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var signData = arguments!!.getSerializable(DATA_KEY) as Sign
        rvMain.layoutManager = LinearLayoutManager(this.context)
        rvMain.adapter = HoroscopeAdapter()
    }
}