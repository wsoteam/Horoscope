package com.wsoteam.horoscopes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wsoteam.horoscopes.models.MatchPair.MatchPair
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.empty.ConnectionFragment
import com.wsoteam.horoscopes.presentation.info.DescriptionFragment
import com.wsoteam.horoscopes.presentation.info.InfoFragment
import com.wsoteam.horoscopes.presentation.main.LoadFragment
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.presentation.match.MatchFragment
import com.wsoteam.horoscopes.presentation.match.MatchResultFragment
import com.wsoteam.horoscopes.presentation.onboard.scan.HandCameraFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.choiceSign
import com.wsoteam.horoscopes.utils.getSignIndexShuffleArray
import com.wsoteam.horoscopes.utils.loger.L
import com.wsoteam.horoscopes.utils.net.state.NetState
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.bnvMain
import kotlinx.android.synthetic.main.black_main_activity.*

class BlackMainActivity : AppCompatActivity(R.layout.black_main_activity),
    MatchFragment.Callbacks, InfoFragment.InfoFragmentCallbacks, HandCameraFragment.Callbacks {

    lateinit var vm: MainVM
    var mainFragment = LoadFragment() as Fragment
    var matchFagment = MatchFragment() as Fragment
    var matchResultFragment = MatchResultFragment() as Fragment
    var infoFragment = InfoFragment() as Fragment
    var descriptionFragment = DescriptionFragment() as Fragment
    var scanFragment = HandCameraFragment() as Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.openMain()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, matchFagment)
            .add(R.id.flContainerMain, infoFragment)
            .commit()

        //supportFragmentManager.beginTransaction().replace(R.id.flContainerMain, LoadFragment()).commit()

        vm = ViewModelProviders.of(this).get(MainVM::class.java)
        vm.setupCachedData()

        if (!NetState.isConnected()) {
            L.log("ConnectionFragment")
            supportFragmentManager.beginTransaction()
                .replace(R.id.flContainerMain, ConnectionFragment()).commit()
        }

        bnvMain.setOnNavigationItemSelectedListener(bnvListener)
        setBNVOwnSignIcon()

    }

    private fun setBNVOwnSignIcon() {
        var index = getSignIndexShuffleArray(PreferencesProvider.getBirthday()!!)
        var iconId = resources.obtainTypedArray(R.array.match_signs_imgs).getResourceId(index, -1)
        bnvMain.menu.getItem(0).setIcon(iconId)
    }

    override fun onResume() {
        super.onResume()
        vm.getLD().observe(this,
            Observer<List<Sign>> {
            })
    }

    fun reloadNetState() {
        if (NetState.isConnected()) {
            supportFragmentManager.beginTransaction().replace(R.id.flContainerMain, LoadFragment())
                .commit()
            vm.reloadData()
        } else {
            NetState.showNetLost(this)
        }
    }

    var bnvListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.bnv_main -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_info -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(supportFragmentManager.findFragmentById(R.id.flContainerMain)!!)
                    .show(infoFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_match -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(supportFragmentManager.findFragmentById(R.id.flContainerMain)!!)
                    .show(matchFagment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_hand -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(supportFragmentManager.findFragmentById(R.id.flContainerMain)!!)
                    .show(scanFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_settings -> {
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }

    }


    override fun openMatchResultFragment(matchPair: MatchPair, ownIndex: Int, matchIndex: Int) {
        matchResultFragment = MatchResultFragment.newInstance(matchPair, ownIndex, matchIndex)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, matchResultFragment)
            .hide(matchFagment)
            .show(matchResultFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun openPremiumScreen() {
    }

    override fun openDescriptionFragment(index: Int) {
        descriptionFragment = DescriptionFragment.newInstance(index)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, descriptionFragment)
            .hide(infoFragment)
            .show(descriptionFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun openNextScreen() {
    }
}