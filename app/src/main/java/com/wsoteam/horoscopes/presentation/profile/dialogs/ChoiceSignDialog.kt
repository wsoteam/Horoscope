package com.wsoteam.horoscopes.presentation.profile.dialogs

import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.pager.fragments.ab.controller.ChoiseSignAdapter
import kotlinx.android.synthetic.main.choice_sign_dialog.*

class ChoiceSignDialog : DialogFragment() {

    private lateinit var adapter : ChoiseSignAdapter
    private lateinit var signNames: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.choice_sign_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ivClose.setOnClickListener {
            dismiss()
        }

        signNames = resources.getStringArray(R.array.names_signs)
        rvChoiceSign.layoutManager = GridLayoutManager(requireContext(), 4)
        adapter = ChoiseSignAdapter(getImgs(), signNames)
    }

    private fun getImgs(): ArrayList<Int> {
        var listImgs = arrayListOf<Int>()
        /*for (i in signNames.indices){
            listImgs.add(signImgs.getResourceId(i, -1))
        }*/
        listImgs.add(R.drawable.img_onboard_aries)
        listImgs.add(R.drawable.img_onboard_taurus)
        listImgs.add(R.drawable.img_onboard_gemini)
        listImgs.add(R.drawable.img_onboard_cancer)
        listImgs.add(R.drawable.img_onboard_leo)
        listImgs.add(R.drawable.img_onboard_virgo)
        listImgs.add(R.drawable.img_onboard_libra)
        listImgs.add(R.drawable.img_onboard_scorpio)
        listImgs.add(R.drawable.img_onboard_sagittarius)
        listImgs.add(R.drawable.img_onboard_capricorn)
        listImgs.add(R.drawable.img_onboard_aquarius)
        listImgs.add(R.drawable.img_onboard_pisces)

        return listImgs
    }
}