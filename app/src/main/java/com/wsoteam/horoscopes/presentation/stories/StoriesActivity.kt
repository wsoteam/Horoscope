package com.wsoteam.horoscopes.presentation.stories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.Today
import com.wsoteam.horoscopes.utils.convert.ActivityToBitmap
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.stories_activity.*
import java.util.*

class StoriesActivity : AppCompatActivity(R.layout.stories_activity) {

    var today : Today? = null
    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = intent.getIntExtra(Config.ID_PRICE, -1)
        today = intent.getSerializableExtra(Config.SIGN_DATA) as Today

        ivSignStories.setImageResource(
            resources.obtainTypedArray(R.array.imgs_signs)
                .getResourceId(index, -1)
        )

        tvTopTitleStories.text = resources.getStringArray(R.array.names_signs)[index]
        tvTitleStories.text = "${getString(R.string.my_horoscope_on)} ${Calendar.getInstance().get(Calendar.DAY_OF_MONTH)} ${Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) }"
        tvTextStories.text = getCutText(today!!.text)

        ivClose.setOnClickListener {
            saveScreenAndSend()
        }

    }

    override fun onPostResume() {
        super.onPostResume()

    }

    private fun saveScreenAndSend() {
        shareImage(ActivityToBitmap.convert(this)!!)
    }

    private fun getCutText(text: String): String {
        var array = text.split(" ")
        var cutString = ""
        for(i in 0..30){
            cutString = "$cutString${array[i]} "
        }
        cutString = "$cutString ..."
        return cutString
    }

    private fun shareImage(uri : Uri){
        var intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
    }
}