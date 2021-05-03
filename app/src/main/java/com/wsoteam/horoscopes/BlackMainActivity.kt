package com.wsoteam.horoscopes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wsoteam.horoscopes.models.MatchPair.MatchPair
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.horoscope.MyHoroscopeFragment
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
import com.wsoteam.horoscopes.utils.net.state.NetState
import kotlinx.android.synthetic.main.black_main_activity.*

class BlackMainActivity : AppCompatActivity(R.layout.black_main_activity),
    MatchFragment.Callbacks, InfoFragment.InfoFragmentCallbacks, HandCameraFragment.Callbacks, MyHoroscopeFragment.MainPageCallbacks {

    lateinit var vm: MainVM
    var matchFragment = MatchFragment()
    var matchResultFragment = MatchResultFragment()
    var infoFragment = InfoFragment()
    var descriptionFragment = DescriptionFragment()
    var scanFragment = HandCameraFragment()

    lateinit var fragmentList: ArrayList<Fragment>

    var signIndex = -1
    var lastPageNumber = 0

    companion object {
        const val MAIN = 0
        const val INFO = 1
        const val MATCH = 2
        const val SCAN = 0
        const val SETTINGS = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.openMain()
        signIndex = choiceSign(PreferencesProvider.getBirthday()!!)
        vm = ViewModelProviders.of(this).get(MainVM::class.java)
        vm.setupCachedData()

        /*if (!NetState.isConnected()) {
            L.log("ConnectionFragment")
            supportFragmentManager.beginTransaction()
                .replace(R.id.flContainerMain, ConnectionFragment()).commit()
        }*/

        bnvBlackMain.setOnNavigationItemSelectedListener(bnvListener)
        setBNVOwnSignIcon()
    }

    private fun bindFragmentManager() {
        for (i in fragmentList.indices) {
            supportFragmentManager.beginTransaction().add(R.id.flContainerMain, fragmentList[i])
                .hide(fragmentList[i]).commit()
        }
        supportFragmentManager.beginTransaction().show(fragmentList[lastPageNumber])
    }

    private fun fillFragmentList(signList: List<Sign>) {
        fragmentList = arrayListOf()
        fragmentList.add(MyHoroscopeFragment.newInstance(signList[signIndex], signIndex))
        fragmentList.add(infoFragment)
        fragmentList.add(matchFragment)
    }

    private fun setBNVOwnSignIcon() {
        var index = getSignIndexShuffleArray(PreferencesProvider.getBirthday()!!)
        var iconId = resources.obtainTypedArray(R.array.match_signs_imgs).getResourceId(index, -1)
        bnvBlackMain.menu.getItem(0).setIcon(iconId)
    }

    override fun onResume() {
        super.onResume()
        vm.getLD().observe(this,
            Observer<List<Sign>> {
                pbMain.visibility = View.GONE
                fillFragmentList(it)
                bindFragmentManager()
                openPage(MAIN)
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
                openPage(MAIN)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_info -> {
                openPage(INFO)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_match -> {
                openPage(MATCH)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_hand -> {
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

    private fun openPage(numberSection: Int) {
        supportFragmentManager.beginTransaction().hide(fragmentList[lastPageNumber]).commit()
        supportFragmentManager.beginTransaction().show(fragmentList[numberSection]).commit()
        lastPageNumber = numberSection
    }


    override fun openMatchResultFragment(matchPair: MatchPair, ownIndex: Int, matchIndex: Int) {
        matchResultFragment = MatchResultFragment.newInstance(matchPair, ownIndex, matchIndex)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, matchResultFragment)
            .hide(matchFragment)
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

    override fun openMatch() {
        bnvBlackMain.selectedItemId = R.id.bnv_match
    }

    override fun openAbout() {
        descriptionFragment = DescriptionFragment.newInstance(signIndex)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, descriptionFragment)
            .show(descriptionFragment)
            .addToBackStack(null)
            .commit()
    }
}