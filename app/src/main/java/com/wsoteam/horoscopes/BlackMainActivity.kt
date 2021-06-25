package com.wsoteam.horoscopes

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wsoteam.horoscopes.models.MatchPair.MatchPair
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.hand.HandResultsFragment
import com.wsoteam.horoscopes.presentation.horoscope.MyHoroscopeFragment
import com.wsoteam.horoscopes.presentation.info.DescriptionFragment
import com.wsoteam.horoscopes.presentation.info.InfoFragment
import com.wsoteam.horoscopes.presentation.main.LoadFragment
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.presentation.match.MatchFragment
import com.wsoteam.horoscopes.presentation.match.MatchResultFragment
import com.wsoteam.horoscopes.presentation.onboard.prem.EnterActivity
import com.wsoteam.horoscopes.presentation.onboard.scan.HandCameraFragment
import com.wsoteam.horoscopes.presentation.profile.ProfileFragment
import com.wsoteam.horoscopes.presentation.profile.TProfileFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.analytics.new.Events
import com.wsoteam.horoscopes.utils.choiceSign
import com.wsoteam.horoscopes.utils.getSignIndexShuffleArray
import com.wsoteam.horoscopes.utils.net.state.NetState
import com.wsoteam.horoscopes.utils.remote.ABDate
import com.wsoteam.horoscopes.utils.text.SignEditor
import kotlinx.android.synthetic.main.black_main_activity.*
import kotlin.random.Random

class BlackMainActivity : AppCompatActivity(R.layout.black_main_activity),
    MatchFragment.Callbacks, InfoFragment.InfoFragmentCallbacks, HandCameraFragment.Callbacks,
    MyHoroscopeFragment.MainPageCallbacks, ProfileFragment.Callbacks {

    lateinit var vm: MainVM

    lateinit var fragmentList: ArrayList<ArrayList<Fragment>>

    lateinit var mainFragments: ArrayList<Fragment>
    lateinit var infoFragments: ArrayList<Fragment>
    lateinit var matchFragments: ArrayList<Fragment>
    lateinit var handCameras: ArrayList<Fragment>
    lateinit var settingsFragments: ArrayList<Fragment>

    var signIndex = -1
    var lastPageNumber = 0

    var isNeedRemove = false

    var isNeedDateFragment = false

    companion object {
        const val MAIN = 0
        const val INFO = 1
        const val MATCH = 2
        const val SCAN = 3
        const val SETTINGS = 4

        const val MATCH_RESULT = "MATCH_RESULT"

        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Events.openPage(Events.main_page)
        //isNeedDateFragment = ABDate.isDateNeed()
        signIndex = choiceSign(PreferencesProvider.getBirthday()!!)
        vm = ViewModelProviders.of(this).get(MainVM::class.java)
        vm.setupCachedData()
        preloadSignImgs()

        /*if (!NetState.isConnected()) {
            L.log("ConnectionFragment")
            supportFragmentManager.beginTransaction()
                .replace(R.id.flContainerMain, ConnectionFragment()).commit()
        }*/

        bnvBlackMain.setOnNavigationItemSelectedListener(bnvListener)
        setBNVOwnSignIcon()

        vm.getLD().observe(this,
            Observer<List<Sign>> {
                var newSignList = getFixList(it)
                bnvBlackMain.visibility = View.VISIBLE
                pbMain.visibility = View.GONE
                fillFragmentList(newSignList)
                bindFragmentManager()
                openPage(MAIN)
            })

        if (PreferencesProvider.isNeedShowInterAfterOnboard && PreferencesProvider.isADEnabled()) {
            AdWorker.showInter()
            PreferencesProvider.isNeedShowInterAfterOnboard = false
        }
    }

    private fun getFixList(it: List<Sign>?): List<Sign> {
        var arrayFixStrings = arrayListOf<String>()
        for (i in resources.getStringArray(R.array.names_signs)) {
            arrayFixStrings.add(resources.getString(R.string.fix_string, i))
        }

        for (i in it!!.indices) {
            for (fixString in arrayFixStrings) {
                it[i].month.text = it[i].month.text.replace(fixString, " ")
            }
        }

        for (i in it.indices){
            it[i].month.text = SignEditor.editText(it[i].month.text)
            it[i].today.text = SignEditor.editText(it[i].today.text)
            it[i].week.text = SignEditor.editText(it[i].week.text)
            it[i].week.career = SignEditor.editText(it[i].week.career)
            it[i].week.love= SignEditor.editText(it[i].week.love)
            it[i].week.money = SignEditor.editText(it[i].week.money)
            it[i].week.wellness = SignEditor.editText(it[i].week.wellness)
        }

        return it
    }

    private fun preloadSignImgs() {
        var signImgs = resources.obtainTypedArray(R.array.sign_draws_info)

        for (i in 0 until signImgs.length()) {
            Glide.with(this)
                .load(signImgs.getResourceId(i, -1))
                .preload()
        }
    }

    private fun bindFragmentManager() {
        for (i in fragmentList.indices) {
            for (j in fragmentList[i].indices) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.flContainerMain, fragmentList[i][j])
                    .hide(fragmentList[i][j]).commit()
            }
        }
        supportFragmentManager.beginTransaction()
            .show(fragmentList[lastPageNumber][fragmentList[lastPageNumber].size - 1])
    }

    private fun fillFragmentList(signList: List<Sign>) {
        mainFragments = arrayListOf()
        infoFragments = arrayListOf()
        matchFragments = arrayListOf()
        handCameras = arrayListOf()
        settingsFragments = arrayListOf()

        fragmentList = arrayListOf()

        var horoFragment = MyHoroscopeFragment.newInstance(signList[signIndex], signIndex)
        mainFragments.add(horoFragment)

        var infoFragment = InfoFragment()
        infoFragments.add(infoFragment)

        var matchFragment = MatchFragment()
        matchFragments.add(matchFragment)

        if (PreferencesProvider.handInfoIndex != PreferencesProvider.EMPTY_HAND_INFO) {
            var handResultsFragment = HandResultsFragment()
            handCameras.add(handResultsFragment)
        } else {
            var handCameraFragment = HandCameraFragment()
            handCameras.add(handCameraFragment)
        }


        if (isNeedDateFragment) {
            settingsFragments.add(ProfileFragment())
        }else{
            settingsFragments.add(TProfileFragment())
        }


        fragmentList.add(mainFragments)
        fragmentList.add(infoFragments)
        fragmentList.add(matchFragments)
        fragmentList.add(handCameras)
        fragmentList.add(settingsFragments)

    }

    private fun setBNVOwnSignIcon() {
        var index = getSignIndexShuffleArray(PreferencesProvider.getBirthday()!!)
        var iconId = resources.obtainTypedArray(R.array.match_signs_imgs).getResourceId(index, -1)
        bnvBlackMain.menu.getItem(0).setIcon(iconId)
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
                Events.openPage(Events.main_page)
                openPage(MAIN)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_info -> {
                Events.openPage(Events.info_page)
                openPage(INFO)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_match -> {
                Events.openPage(Events.match_page)
                openPage(MATCH)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_hand -> {
                Events.openPage(Events.scan_page)
                if (allPermissionsGranted()) {
                    openPage(SCAN)
                    if (fragmentList[SCAN][fragmentList[SCAN].size - 1] is HandCameraFragment) {
                        (fragmentList[SCAN][fragmentList[SCAN].size - 1] as HandCameraFragment).startCamera()
                    }
                    return@OnNavigationItemSelectedListener true
                } else {
                    ActivityCompat.requestPermissions(
                        this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                    )
                    return@OnNavigationItemSelectedListener false
                }
            }
            R.id.bnv_settings -> {
                Events.openPage(Events.settings_page)
                openPage(SETTINGS)
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                bnvBlackMain.selectedItemId = R.id.bnv_hand
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openPage(numberSection: Int) {
        supportFragmentManager.beginTransaction()
            .hide(fragmentList[lastPageNumber][fragmentList[lastPageNumber].size - 1]).commit()
        if (isNeedRemove) {
            fragmentList[lastPageNumber].removeAt(fragmentList[lastPageNumber].size - 1)
        }
        supportFragmentManager.beginTransaction()
            .show(fragmentList[numberSection][fragmentList[numberSection].size - 1]).commit()

        lastPageNumber = numberSection
        isNeedRemove = false
    }


    override fun openMatchResultFragment(matchPair: MatchPair, ownIndex: Int, matchIndex: Int) {
        var matchResultFragment = MatchResultFragment.newInstance(matchPair, ownIndex, matchIndex)
        matchFragments.add(matchResultFragment)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, matchFragments[matchFragments.size - 1])
            .hide(matchFragments[0])
            .show(matchFragments[matchFragments.size - 1])
            .commit()
    }

    override fun openPremiumScreenMatch() {
        startActivity(EnterActivity.getIntent(this, EnterActivity.from_match))
    }

    override fun openPremFromMain() {
        startActivity(EnterActivity.getIntent(this, EnterActivity.from_main_page))
    }

    override fun openPremFromHand() {
        startActivity(EnterActivity.getIntent(this, EnterActivity.from_scan_page))
    }

    override fun openDescriptionFragment(index: Int) {
        var descriptionFragment = DescriptionFragment.newInstance(index)
        infoFragments.add(descriptionFragment)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, infoFragments[infoFragments.size - 1])
            .hide(infoFragments[0])
            .show(infoFragments[infoFragments.size - 1])
            .commit()
    }

    override fun openNextScreen() {
        PreferencesProvider.handInfoIndex = Random.nextInt(0, 10)
        var handResultFragment = HandResultsFragment()
        handCameras.add(handResultFragment)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, fragmentList[SCAN][fragmentList[SCAN].size - 1])
            .remove(fragmentList[SCAN][0])
            .show(fragmentList[SCAN][fragmentList[SCAN].size - 1])
            .commit()

        fragmentList[SCAN].removeAt(0)
    }

    override fun openMatch() {
        bnvBlackMain.selectedItemId = R.id.bnv_match
    }

    override fun openAbout() {
        var descriptionFragment = DescriptionFragment.newInstance(signIndex)
        mainFragments.add(descriptionFragment)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainerMain, mainFragments[mainFragments.size - 1])
            .hide(mainFragments[0])
            .show(mainFragments[mainFragments.size - 1])
            .commit()
    }

    override fun refreshSing() {
        signIndex = choiceSign(PreferencesProvider.getBirthday()!!)
        setBNVOwnSignIcon()

        for (i in matchFragments.indices) {
            matchFragments.removeAt(i)
        }

        for (i in mainFragments.indices) {
            mainFragments.removeAt(i)
        }

        matchFragments.add(MatchFragment())
        mainFragments.add(MyHoroscopeFragment.newInstance(vm.getLD().value!![signIndex], signIndex))

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainerMain, matchFragments[0])
            .add(R.id.flContainerMain, mainFragments[0])
            .hide(matchFragments[0])
            .hide(mainFragments[0])
            .commit()
    }


    override fun onBackPressed() {
        if (this::fragmentList.isInitialized) {
            when (bnvBlackMain.selectedItemId) {
                R.id.bnv_main -> {
                    if (fragmentList[0].size > 1) {
                        isNeedRemove = true
                        bnvBlackMain.selectedItemId = R.id.bnv_main
                    } else {
                        super.onBackPressed()
                    }
                }
                R.id.bnv_info -> {
                    if (fragmentList[1].size > 1) {
                        isNeedRemove = true
                        bnvBlackMain.selectedItemId = R.id.bnv_info
                    } else {
                        bnvBlackMain.selectedItemId = R.id.bnv_main
                    }
                }
                R.id.bnv_match -> {
                    if (fragmentList[2].size > 1) {
                        isNeedRemove = true
                        bnvBlackMain.selectedItemId = R.id.bnv_match
                    } else {
                        bnvBlackMain.selectedItemId = R.id.bnv_main
                    }
                }
                R.id.bnv_hand -> {
                    bnvBlackMain.selectedItemId = R.id.bnv_main
                }
                R.id.bnv_settings -> {
                    bnvBlackMain.selectedItemId = R.id.bnv_main
                }
            }
        } else {
            super.onBackPressed()
        }
    }
}