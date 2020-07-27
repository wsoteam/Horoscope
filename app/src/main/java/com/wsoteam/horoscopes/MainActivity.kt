package com.wsoteam.horoscopes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.wsoteam.horoscopes.presentation.main.MainFragment
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.presentation.settings.SettingsFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ZodiacChoiser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var vm: MainVM

    var listIndexes = listOf<Int>(
        R.id.nav_aries, R.id.nav_taurus,
        R.id.nav_gemini, R.id.nav_cancer, R.id.nav_leo, R.id.nav_virgo,
        R.id.nav_libra, R.id.nav_scorpio, R.id.nav_sagittarius, R.id.nav_sagittarius,
        R.id.nav_capricorn, R.id.nav_aquarius, R.id.nav_pisces
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProviders.of(this).get(MainVM::class.java)

        supportFragmentManager.beginTransaction().replace(R.id.clContainer, SettingsFragment()).commit()

        var toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.nav_app_bar_open_drawer_description,
            R.string.nav_app_bar_open_drawer_description
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        setFirstUI()
    }

    private fun setFirstUI() {
        setSignUI(ZodiacChoiser.choiceSign(PreferencesProvider.getBirthday()!!))
    }

    private fun setSignUI(it: Int) {
        nav_view.menu.getItem(it + 1).isChecked = true
        onNavigationItemSelected(nav_view.menu.getItem(it + 1))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.e("LOL", listIndexes.indexOf(item.itemId).toString())

        if (item.itemId == R.id.nav_gemini){

        }
        return true
    }


}

