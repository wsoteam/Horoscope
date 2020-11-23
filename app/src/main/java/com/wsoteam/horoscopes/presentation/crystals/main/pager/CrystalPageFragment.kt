package com.wsoteam.horoscopes.presentation.crystals.main.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.crystal_page_fragment.*

class CrystalPageFragment : Fragment(R.layout.crystal_page_fragment) {

    companion object{
        const val TAG_IMG_ID = "TAG_IMG_ID"
        const val TAG_NAME_ID = "TAG_NAME_ID"

        fun newInstance(imgId : Int, name : String) : CrystalPageFragment{
            var bundle = Bundle()
            bundle.putInt(TAG_IMG_ID, imgId)
            bundle.putString(TAG_NAME_ID, name)
            var fragment = CrystalPageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivCrystalMain.setImageResource(requireArguments().getInt(TAG_IMG_ID))
        tvNameCrystal.text = requireArguments().getString(TAG_NAME_ID)
    }
}