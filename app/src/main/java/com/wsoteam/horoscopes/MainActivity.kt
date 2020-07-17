package com.wsoteam.horoscopes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wsoteam.horoscopes.presentation.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.clFragmentContainer, SettingsFragment()).commit()
    }
}
