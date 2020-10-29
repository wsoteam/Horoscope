package com.wsoteam.horoscopes.presentation.stories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.convert.ActivityToBitmap
import com.wsoteam.horoscopes.utils.loger.L
import kotlinx.android.synthetic.main.stories_activity.*

class StoriesActivity : AppCompatActivity(R.layout.stories_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun shareImage(uri : Uri){
        var intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
    }
}