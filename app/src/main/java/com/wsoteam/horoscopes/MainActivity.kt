package com.wsoteam.horoscopes

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.navigation.NavigationView
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.main.MainFragment
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.presentation.premium.PremiumHostActivity
import com.wsoteam.horoscopes.presentation.settings.SettingsFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.choiceSign
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.settings_fragment.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var vm: MainVM
    var birthSignIndex = -1

    var listIndexes = listOf<Int>(
        R.id.nav_aries, R.id.nav_taurus,
        R.id.nav_gemini, R.id.nav_cancer, R.id.nav_leo, R.id.nav_virgo,
        R.id.nav_libra, R.id.nav_scorpio, R.id.nav_sagittarius,
        R.id.nav_capricorn, R.id.nav_aquarius, R.id.nav_pisces
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (PreferencesProvider.isADEnabled()) {
            ivToolPrem.visibility = View.VISIBLE
        } else {
            ivToolPrem.visibility = View.GONE
        }

        vm = ViewModelProviders.of(this).get(MainVM::class.java)
        vm.setupCachedData()
        vm.getLD().observe(this,
            Observer<List<Sign>> {
                setFirstUI()
            })

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


    }

    private fun setFirstUI() {
        birthSignIndex = choiceSign(PreferencesProvider.getBirthday()!!)
        setSelectedItem(birthSignIndex)
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
        when {
            index != -1 -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.flContainer,
                        MainFragment.newInstance(index, vm.getLD().value!![index])
                    )
                    .commit()
                tvToolTitle.text =
                    resources.getStringArray(R.array.names_signs)[listIndexes.indexOf(item.itemId)]
                bindToolbar(listIndexes.indexOf(item.itemId))
                if (llTools.visibility != View.VISIBLE){
                    llTools.visibility = View.VISIBLE
                }
                drawer_layout.closeDrawers()
                return true
            }
            item.itemId == R.id.nav_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.flContainer, SettingsFragment())
                    .commit()
                tvToolTitle.text = getString(R.string.settings)
                if (llTools.visibility == View.VISIBLE){
                    llTools.visibility = View.INVISIBLE
                }
                drawer_layout.closeDrawers()
                return true
            }
            else -> {
                openPrem()
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
            DrawableCompat.setTint(drawable, resources.getColor(R.color.active_tool_sign))
        } else {
            DrawableCompat.setTint(drawable, resources.getColor(R.color.inactive_tool_sign))
        }
        ivToolSign.setImageDrawable(drawable)
    }

    override fun onBackPressed() {
        if (!drawer_layout.isDrawerOpen(GravityCompat.START)){
            super.onBackPressed()
        }else{
            drawer_layout.closeDrawers()
        }
    }
}

