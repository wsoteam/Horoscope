package com.wsoteam.horoscopes.presentation.onboard.scan

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.prem.EnterActivity
import kotlinx.android.synthetic.main.hand_camera_activity.*
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HandCameraActivity : AppCompatActivity(R.layout.hand_camera_host_activity),
    HandCameraFragment.Callbacks {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainer, HandCameraFragment())
            .commit()

    }

    override fun openNextScreen() {
        startActivity(Intent(this, EnterActivity::class.java))
        finishAffinity()
    }
}