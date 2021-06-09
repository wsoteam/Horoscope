package com.wsoteam.horoscopes.presentation.onboard.scan

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.BlackMainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.camera.ObjectDetectorAnalyzer
import com.wsoteam.horoscopes.presentation.hand.dialogs.UnlockScanDialog
import com.wsoteam.horoscopes.presentation.match.dialogs.UnlockDialog
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.analytics.new.Events
import com.wsoteam.horoscopes.utils.match.MatchConverter
import kotlinx.android.synthetic.main.hand_camera_activity.*
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HandCameraFragment : Fragment(R.layout.hand_camera_activity) {

    interface Callbacks {
        fun openNextScreen()
        fun openPremFromHand()
    }

    private lateinit var timer: CountDownTimer
    private var lastDetect = -1L
    private lateinit var bitmap: Bitmap

    private val DETECTOR_HAND_LABEL = "person"
    private val LOST_DETECT_INTERVAL = 500L

    private lateinit var executor: ExecutorService


    private val objectDetectorConfig = ObjectDetectorAnalyzer.Config(
        minimumConfidence = 0.5f,
        numDetection = 10,
        inputSize = 300,
        isQuantized = true,
        modelFile = "detect.tflite",
        labelsFile = "labelmap.txt"
    )



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executor = Executors.newSingleThreadExecutor()

        disableHandDetected()
        startPreviewUpdater()

        ivTakePhoto.setOnClickListener {
            scanHand()
        }


    }

    private fun getProcessCameraProvider(onDone: (ProcessCameraProvider) -> Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            Runnable { onDone.invoke(cameraProviderFuture.get()) },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun bindCamera(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(
            executor,
            ObjectDetectorAnalyzer(App.getInstance().applicationContext, objectDetectorConfig, ::onDetectionResult)
        )

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        cameraProvider.unbindAll()

        cameraProvider.bindToLifecycle(
            this,
            cameraSelector,
            imageAnalysis,
            preview
        )

        preview.setSurfaceProvider(viewFinder.createSurfaceProvider())
    }

    private fun onDetectionResult(result: ObjectDetectorAnalyzer.Result) {
        if (result?.objects?.size > 0){
            for (i in result.objects.indices){
                Log.e("LOL", result.objects[i].title)
                if (result.objects[i].title == DETECTOR_HAND_LABEL){
                    enableHandDetected()
                }
            }

        }
    }

    private fun scanHand() {
        bitmap = viewFinder.bitmap!!
        Glide.with(this).load(bitmap).into(ivPreview)
        ivPreview.visibility = View.VISIBLE
        timer.cancel()
        startScanning()
    }

    private fun startScanning() {
        ivScan.visibility = View.VISIBLE
        var objectAnimator = ObjectAnimator.ofFloat(ivScan.translationY, 900f)
        objectAnimator.duration = 5_000L
        objectAnimator.addUpdateListener {
            ivScan?.translationY = it.animatedValue.toString().toFloat()
        }

        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                (requireActivity() as Callbacks).openNextScreen()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        objectAnimator.start()

    }

    private fun startPreviewUpdater() {
        timer = object : CountDownTimer(10_000, 150) {
            override fun onFinish() {
                timer.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                try {
                    if (tvIdicator.isEnabled && Calendar.getInstance().timeInMillis - lastDetect >= LOST_DETECT_INTERVAL) {
                        disableHandDetected()
                    }
                } catch (ex: java.lang.Exception) {

                }
            }
        }
        timer.start()
    }




    fun startCamera() {
        getProcessCameraProvider(::bindCamera)
    }



    private fun disableHandDetected() {
        tvIdicator?.isEnabled = false
        tvIdicator?.text = getString(R.string.searching_palm)
        ivTakePhoto?.isEnabled = false
        ivHand?.visibility = View.VISIBLE
    }

    private fun enableHandDetected() {
        tvIdicator?.isEnabled = true
        tvIdicator?.text = getString(R.string.searching_palm_enabled)
        ivTakePhoto?.isEnabled = true
        lastDetect = Calendar.getInstance().timeInMillis
        ivHand?.visibility = View.INVISIBLE
    }


    companion object {
        private const val FROM_TAG = "FROM_TAG"

        fun newInstance(isFromInsideApp: Boolean): HandCameraFragment {
            var args = Bundle().apply {
                putBoolean(FROM_TAG, isFromInsideApp)
            }
            return HandCameraFragment().apply {
                arguments = args
            }
        }
    }
}