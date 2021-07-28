package com.lolkekteam.astrohuastro.presentation.empty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.lolkekteam.astrohuastro.MainActivity
import com.lolkekteam.astrohuastro.R
import kotlinx.android.synthetic.main.connection_fragment.*

class ConnectionFragment : Fragment(R.layout.connection_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvReload.setOnClickListener {
            (activity as MainActivity).reloadNetState()
        }

    }
}