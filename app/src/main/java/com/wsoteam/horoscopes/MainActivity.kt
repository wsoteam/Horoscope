package com.wsoteam.horoscopes

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.ball.BallActivity
import com.wsoteam.horoscopes.presentation.empty.ConnectionFragment
import com.wsoteam.horoscopes.presentation.main.LoadFragment
import com.wsoteam.horoscopes.presentation.main.MainFragment
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.presentation.main.ld.ScreensLD
import com.wsoteam.horoscopes.presentation.premium.PremiumHostActivity
import com.wsoteam.horoscopes.presentation.settings.SettingsActivity
import com.wsoteam.horoscopes.presentation.settings.SettingsFragment
import com.wsoteam.horoscopes.presentation.settings.dialogs.InfoDialog
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.choiceSign
import com.wsoteam.horoscopes.utils.net.state.NetState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var vm: MainVM
    var birthSignIndex = -1
    var lastIndex = 0

    /*var screensObserver = Observer<Int> {
        *//*if (it == ScreensLD.LOCK_INDEX && PreferencesProvider.isADEnabled()) {
            (supportFragmentManager.findFragmentById(R.id.flContainer) as MainFragment).setOpenTab()
            openPrem()
        }*//*
    }*/

    var listIndexes = listOf<Int>(
        R.id.nav_aries, R.id.nav_taurus,
        R.id.nav_gemini, R.id.nav_cancer, R.id.nav_leo, R.id.nav_virgo,
        R.id.nav_libra, R.id.nav_scorpio, R.id.nav_sagittarius,
        R.id.nav_capricorn, R.id.nav_aquarius, R.id.nav_pisces
    )

    var bnvListener = BottomNavigationView.OnNavigationItemSelectedListener {

        when(it.itemId){
            R.id.bnv_main -> {
                changeNavigationState(true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_prem ->  {
                changeNavigationState(false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_balls -> {
                changeNavigationState(false)
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }

    }

    private fun changeNavigationState(isVisible : Boolean){
        var container = clContainer as ConstraintLayout
        var params = container.layoutParams as CoordinatorLayout.LayoutParams
        if (isVisible){
            params.behavior = AppBarLayout.ScrollingViewBehavior()
            container.requestLayout()
            ablMain.visibility = View.VISIBLE
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }else{
            params.behavior = null
            container.requestLayout()
            ablMain.visibility = View.GONE
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SubscriptionProvider.startGettingPrice(Config.ID_PRICE)
        if (PreferencesProvider.isADEnabled()) {
            adView.visibility = View.VISIBLE
            adView.loadAd(AdRequest.Builder().build())
        }
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, LoadFragment()).commit()
        if (PreferencesProvider.isADEnabled()) {
            ivToolPrem.visibility = View.VISIBLE
        } else {
            ivToolPrem.visibility = View.GONE
        }

        vm = ViewModelProviders.of(this).get(MainVM::class.java)
        vm.setupCachedData()


        if (!NetState.isConnected()) {
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


        ivToolPrem.setOnClickListener {
            openPrem()
        }

        ivToolSign.setOnClickListener {
            if (choiceSign(PreferencesProvider.getBirthday()!!) != listIndexes.indexOf(nav_view.checkedItem!!.itemId)) {
                setSelectedItem(choiceSign(PreferencesProvider.getBirthday()!!))
            }
        }

        ivToolShare.setOnClickListener {
            share()
        }


        bnvMain.setOnNavigationItemSelectedListener(bnvListener)
    }

    override fun onResume() {
        super.onResume()
        vm.getLD().observe(this,
            Observer<List<Sign>> {
                Log.e("LOL", "observe")
                setFirstUI()
            })
    }

    private fun share() {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT, PreferencesProvider.getLastText() + "\n"
                    + "https://play.google.com/store/apps/details?id="
                    + packageName
        )
        startActivity(intent)
    }


    fun reloadNetState() {
        if (NetState.isConnected()) {
            supportFragmentManager.beginTransaction().replace(R.id.flContainer, LoadFragment())
                .commit()
            vm.reloadData()
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            AdWorker.init(this)
        } else {
            NetState.showNetLost(this)
        }
    }


    private fun setFirstUI() {
        if (birthSignIndex != choiceSign(PreferencesProvider.getBirthday()!!)) {
            birthSignIndex = choiceSign(PreferencesProvider.getBirthday()!!)
            setSelectedItem(birthSignIndex)
        }
    }

    private fun setSelectedItem(index: Int) {
        nav_view.menu.getItem(index + 1).isChecked = true
        onNavigationItemSelected(nav_view.menu.getItem(index + 1))
    }

    private fun openPrem() {
        startActivity(
            Intent(this, PremiumHostActivity::class.java).putExtra(
                Config.OPEN_PREM,
                Config.OPEN_PREM_FROM_MAIN
            )
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val index = listIndexes.indexOf(item.itemId)
        var transaction = supportFragmentManager.beginTransaction()
        when {
            index != -1 -> {
                transaction
                    .replace(
                        R.id.flContainer,
                        MainFragment.newInstance(index, vm.getLD().value!![index])
                    )
                    .commit()
                tvToolTitle.text =
                    resources.getStringArray(R.array.names_signs)[listIndexes.indexOf(item.itemId)]
                bindToolbar(listIndexes.indexOf(item.itemId))
                if (llTools.visibility != View.VISIBLE) {
                    llTools.visibility = View.VISIBLE
                }
                drawer_layout.closeDrawers()
                lastIndex = index
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
                startActivity(Intent(this, SettingsActivity::class.java))
                drawer_layout.closeDrawers()
                return false
            }
            else -> {
                if (PreferencesProvider.isADEnabled()) {
                    openPrem()
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

    override fun onDestroy() {
        super.onDestroy()
        //ScreensLD.screensLD.removeObserver(screensObserver)
    }

}

