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
import com.wsoteam.horoscopes.BlackMainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.prem.EnterActivity
import com.wsoteam.horoscopes.utils.analytics.new.Events
import kotlinx.android.synthetic.main.hand_camera_activity.*
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HandCameraActivity : AppCompatActivity(R.layout.hand_camera_host_activity),
    HandCameraFragment.Callbacks {

    lateinit var handCameraFragment: HandCameraFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handCameraFragment = HandCameraFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flContainer, handCameraFragment)
            .commit()


        if (allPermissionsGranted()) {
            handCameraFragment.startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

    }

    override fun openNextScreen() {
        Events.successOnboardScan()
        startActivity(EnterActivity.getIntent(this, EnterActivity.from_onboard))
        finishAffinity()
    }

    override fun openPremFromHand() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Events.successScanPermOnboard()
                handCameraFragment.startCamera()
            } else {
                Events.failureScanPermOnboard()
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                onBackPressed()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}