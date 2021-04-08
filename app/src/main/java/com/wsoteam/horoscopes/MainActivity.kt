package com.wsoteam.horoscopes

import android.app.*
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.astrologer.WomanActivity
import com.wsoteam.horoscopes.presentation.astrologer.hands.HandsActivity
import com.wsoteam.horoscopes.presentation.ball.BallFragment
import com.wsoteam.horoscopes.presentation.empty.ConnectionFragment
import com.wsoteam.horoscopes.presentation.main.LoadFragment
import com.wsoteam.horoscopes.presentation.main.MainFragment
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.presentation.onboard.HostActivity
import com.wsoteam.horoscopes.presentation.premium.PremiumFragment
import com.wsoteam.horoscopes.presentation.premium.PremiumHostActivity
import com.wsoteam.horoscopes.presentation.settings.SettingsActivity
import com.wsoteam.horoscopes.presentation.settings.dialogs.InfoDialog
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.BannerFrequency
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.experior.Experior
import com.wsoteam.horoscopes.utils.choiceSign
import com.wsoteam.horoscopes.utils.interceptor.ShareBroadcast
import com.wsoteam.horoscopes.utils.loger.L
import com.wsoteam.horoscopes.utils.net.state.NetState
import com.wsoteam.horoscopes.utils.remote.ABConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var vm: MainVM
    var birthSignIndex = -1
    var lastIndex = 0

    var isFirstSetUp = true

    var premFragment = PremiumFragment()
    var mainFragment = LoadFragment() as Fragment
    var ballFragment = BallFragment() as Fragment

    var listIndexes = listOf<Int>(
        R.id.nav_aries, R.id.nav_taurus,
        R.id.nav_gemini, R.id.nav_cancer, R.id.nav_leo, R.id.nav_virgo,
        R.id.nav_libra, R.id.nav_scorpio, R.id.nav_sagittarius,
        R.id.nav_capricorn, R.id.nav_aquarius, R.id.nav_pisces
    )

    var bnvListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.bnv_main -> {
                openMainFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_prem -> {
                PreferencesProvider.setBeforePremium(Analytic.nav_premium)
                openPremiumFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_balls -> {
                openBallFragment()
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }

    }

    fun openMainFragment() {
        L.log("openMainFragment")
        var transaction = supportFragmentManager.beginTransaction()
        if (premFragment.isAdded) {
            transaction.hide(premFragment)
        }
        if (ballFragment.isAdded) {
            transaction.hide(ballFragment)
        }
        if (!mainFragment.isAdded) {
            transaction.add(R.id.flContainer, mainFragment)
        }
        transaction.show(mainFragment).commit()
        window.statusBarColor = Color.rgb(199, 189, 179)
        changeNavigationState(true)
        if (PreferencesProvider.isADEnabled() && BannerFrequency.needShow()) {
            adView.visibility = View.VISIBLE
        }
    }

    fun openPremiumFragment() {
        L.log("openPremiumFragment")
        var transaction = supportFragmentManager.beginTransaction()
        if (mainFragment.isAdded) {
            transaction.hide(mainFragment)
        }
        if (ballFragment.isAdded) {
            transaction.hide(ballFragment)
        }
        if (!premFragment.isAdded) {
            transaction.add(R.id.flContainer, premFragment)
        }
        transaction.show(premFragment).commit()
        window.statusBarColor = Color.rgb(199, 189, 179)
        changeNavigationState(false)
        Analytic.showPrem(PreferencesProvider.getBeforePremium()!!)
        adView.visibility = View.GONE
    }

    fun openBallFragment() {
        L.log("openBallFragment")
        Analytic.showBalls()
        var transaction = supportFragmentManager.beginTransaction()
        if (mainFragment.isAdded) {
            transaction.hide(mainFragment)
        }
        if (premFragment.isAdded) {
            transaction.hide(premFragment)
        }
        if (!ballFragment.isAdded) {
            transaction.add(R.id.flContainer, ballFragment)
        }
        transaction.show(ballFragment).commit()
        window.statusBarColor = Color.rgb(0, 0, 0)
        changeNavigationState(false)
        if (PreferencesProvider.isADEnabled() && BannerFrequency.needShow()) {
            adView.visibility = View.VISIBLE
        }
    }


    private fun changeNavigationState(isVisible: Boolean) {
        L.log("changeNavigationState -- $isVisible")
        var container = clContainer as ConstraintLayout
        var params = container.layoutParams as CoordinatorLayout.LayoutParams
        if (isVisible) {
            params.behavior = AppBarLayout.ScrollingViewBehavior()
            container.requestLayout()
            ablMain.visibility = View.VISIBLE
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            params.behavior = null
            container.requestLayout()
            ablMain.visibility = View.GONE
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    fun openPremSection() {
        bnvMain.selectedItemId = R.id.bnv_prem
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Analytic.openMain()

        if (PreferencesProvider.isNeedNewTheme) {
            setTheme(R.style.WhiteTheme)
        }
        setContentView(R.layout.activity_main)

        if (PreferencesProvider.isNeedNewTheme) {
            setWhiteViews()
        }

        if (Config.FOR_TEST){
            ivToolShare.visibility = View.GONE
        }

        Log.e("LOL", "Main create")
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainer, PremiumFragment())
            .add(R.id.flContainer, BallFragment())
            .commit()

        if (PreferencesProvider.isADEnabled() && BannerFrequency.needShow() && !Config.FOR_TEST) {
            adView.visibility = View.VISIBLE
            adView.loadAd(AdRequest.Builder().build())
        }


        supportFragmentManager.beginTransaction().replace(R.id.flContainer, LoadFragment()).commit()
        if (PreferencesProvider.isADEnabled()) {
            ivToolPrem.visibility = View.VISIBLE
        } else {
            ivToolPrem.visibility = View.GONE
        }

        if (PreferencesProvider.getVersion() != ABConfig.A && PreferencesProvider.getVersion() != ABConfig.G) {
            ivToolAstro.visibility = View.VISIBLE
            var intent = when (PreferencesProvider.getVersion()) {
                ABConfig.B, ABConfig.C, ABConfig.H -> Intent(this@MainActivity, WomanActivity::class.java)
                ABConfig.E, ABConfig.D, ABConfig.F -> Intent(this@MainActivity, HandsActivity::class.java)
                else -> Intent(this@MainActivity, WomanActivity::class.java)
            }
            ivToolAstro.setOnClickListener {
                startActivity(intent)
            }
        }

        vm = ViewModelProviders.of(this).get(MainVM::class.java)
        vm.setupCachedData()

        if (!NetState.isConnected()) {
            L.log("ConnectionFragment")
            supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, ConnectionFragment()).commit()
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }


        var toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.nav_app_bar_open_drawer_description,
            R.string.nav_app_bar_open_drawer_description
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
                Experior.trackBurgerMenu()
            }
        })


        ivToolPrem.setOnClickListener {
            PreferencesProvider.setBeforePremium(Analytic.crown_premium)
            openPrem()
            Experior.trackMainPremium()
            //bnvMain.selectedItemId = R.id.bnv_prem
        }

        ivToolSign.setOnClickListener {
            if (choiceSign(PreferencesProvider.getBirthday()!!) != listIndexes.indexOf(nav_view.checkedItem!!.itemId)) {
                setSelectedItem(choiceSign(PreferencesProvider.getBirthday()!!))
            }
        }

        ivToolShare.setOnClickListener {
            Experior.trackMainShare()
            share()
        }


        bnvMain.setOnNavigationItemSelectedListener(bnvListener)
        if (!PreferencesProvider.isADEnabled()) {
            bnvMain.menu.removeItem(R.id.bnv_prem)
            nav_view.menu.removeItem(R.id.nav_off_ads)
        }

        startActivity(Intent(this, HostActivity::class.java))
    }

    private fun setWhiteViews() {
        toolbar.setBackgroundColor(resources.getColor(R.color.white))
        nav_view.itemBackground = resources.getDrawable(R.drawable.selector_drawer_back_white)
        nav_view.itemTextColor = resources.getColorStateList(R.color.selector_drawer_text_white)
        nav_view.itemIconTintList = resources.getColorStateList(R.color.selector_drawer_text_white)
        nav_view.background = resources.getDrawable(R.color.white)
        nav_view.setItemTextAppearance(R.style.DrawerItemTextWhite)
    }

    private fun changeNavigationDrawer() {
    }

    override fun onResume() {
        super.onResume()
        vm.getLD().observe(this,
            Observer<List<Sign>> {
                Log.e("LOL", "observe")
                setFirstUI()
            })
        //kek()
        //lol()
    }

    private fun share() {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT, PreferencesProvider.getLastText() + "\n"
                    + "https://play.google.com/store/apps/details?id="
                    + packageName
        )

        var receiver = Intent(Config.ACTION_SHARE)
        receiver.putExtra("test", "test")

        var pendingIntent = PendingIntent
            .getBroadcast(this, 0, receiver, PendingIntent.FLAG_UPDATE_CURRENT)

        var chooser = Intent.createChooser(intent, "Share", pendingIntent.intentSender)
        this.registerReceiver(ShareBroadcast(), IntentFilter(Config.ACTION_SHARE))
        startActivity(chooser)
    }


    fun reloadNetState() {
        if (NetState.isConnected()) {
            supportFragmentManager.beginTransaction().replace(R.id.flContainer, LoadFragment())
                .commit()
            vm.reloadData()
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            NetState.showNetLost(this)
        }
    }


    private fun setFirstUI() {
        L.log("setFirstUI")
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        if (birthSignIndex != choiceSign(PreferencesProvider.getBirthday()!!)) {
            L.log("birthSignIndex !=")
            birthSignIndex = choiceSign(PreferencesProvider.getBirthday()!!)
            setSelectedItem(birthSignIndex)
        } else {
            L.log("birthSignIndex ==")
        }
    }

    private fun setSelectedItem(index: Int) {
        L.log("setSelectedItem")
        nav_view.menu.getItem(index).isChecked = true
        onNavigationItemSelected(nav_view.menu.getItem(index))
    }

    fun openPrem() {
        startActivity(
            Intent(this, PremiumHostActivity::class.java).putExtra(
                Config.OPEN_PREM,
                Config.OPEN_PREM_FROM_MAIN
            )
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        L.log("onNavigationItemSelected")
        val index = listIndexes.indexOf(item.itemId)
        var transaction = supportFragmentManager.beginTransaction()
        when {
            index != -1 -> {
                L.log("sign item")
                mainFragment = MainFragment.newInstance(index, vm.getLD().value!![index])
                transaction
                    .replace(
                        R.id.flContainer,
                        mainFragment
                    )
                    .commit()
                tvToolTitle.text =
                    resources.getStringArray(R.array.names_signs)[listIndexes.indexOf(item.itemId)]
                if (!isFirstSetUp) {
                    Experior.trackBurgerSignProp(tvToolTitle.text.toString())
                }
                bindToolbar(listIndexes.indexOf(item.itemId))
                if (llTools.visibility != View.VISIBLE) {
                    llTools.visibility = View.VISIBLE
                }
                drawer_layout.closeDrawers()
                lastIndex = index
                isFirstSetUp = false
                return true
            }
            item.itemId == R.id.nav_settings -> {
                /*transaction
                    .add(R.id.flContainer, SettingsFragment())
                if (supportFragmentManager.backStackEntryCount == 0){
                    transaction.addToBackStack(null)
                }
                transaction.commit()

                tvToolTitle.text = getString(R.string.settings)
                if (llTools.visibility == View.VISIBLE) {
                    llTools.visibility = View.INVISIBLE
                }
                drawer_layout.closeDrawers()*/
                L.log("nav_settings")
                startActivity(Intent(this, SettingsActivity::class.java))
                drawer_layout.closeDrawers()
                Experior.trackBurgerSettings()
                return false
            }
            else -> {
                if (PreferencesProvider.isADEnabled()) {
                    PreferencesProvider.setBeforePremium(Analytic.burger_premium)
                    openPrem()
                    Experior.trackBurgerPremium()
                } else {
                    InfoDialog().show(supportFragmentManager, "")
                }
                return false
            }
        }
    }

    private fun bindToolbar(indexOf: Int) {
        var drawable = VectorDrawableCompat.create(
            resources,
            resources.obtainTypedArray(R.array.icons_signs).getResourceId(birthSignIndex, -1), null
        ) as Drawable
        drawable = DrawableCompat.wrap(drawable)
        if (indexOf == birthSignIndex) {
            DrawableCompat.setTint(drawable, resources.getColor(R.color.inactive_tool_sign))
        } else {
            DrawableCompat.setTint(drawable, resources.getColor(R.color.active_tool_sign))
        }
        ivToolSign.setImageDrawable(drawable)
    }

    override fun onBackPressed() {
        L.log("onBackPressed")
        if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
            if (supportFragmentManager.backStackEntryCount != 0) {
                supportFragmentManager
                    .popBackStack()
                setSelectedItem(lastIndex)
            } else {
                super.onBackPressed()
            }
        } else {
            drawer_layout.closeDrawers()
        }
    }






}

