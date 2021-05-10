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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.hand_camera_activity.*
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HandCameraFragment : Fragment(R.layout.hand_camera_activity) {

    interface Callbacks {
        fun openNextScreen()
    }

    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var objectDetector: ObjectDetector
    private lateinit var timer: CountDownTimer
    private var lastDetect = -1L
    private lateinit var bitmap: Bitmap

    private val DETECTOR_HAND_LABEL = "Band Aid"
    private val LOST_DETECT_INTERVAL = 500L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObjectDetector()
        cameraExecutor = Executors.newSingleThreadExecutor()
        disableHandDetected()

        startPreviewUpdater()

        ivTakePhoto.setOnClickListener {
            bitmap = viewFinder.bitmap!!
            Glide.with(this).load(bitmap).into(ivPreview)
            ivPreview.visibility = View.VISIBLE
            timer.cancel()
            startScanning()
        }
    }

    private fun startScanning() {
        ivScan.visibility = View.VISIBLE
        var objectAnimator = ObjectAnimator.ofFloat(ivScan.translationY, 900f)
        objectAnimator.duration = 5_000L
        objectAnimator.addUpdateListener {
            ivScan.translationY = it.animatedValue.toString().toFloat()
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

    private fun initObjectDetector() {
        val localModel = LocalModel.Builder().setAssetFilePath("lite.tflite").build()

        val options = CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(CustomObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .setClassificationConfidenceThreshold(0.1f)
            .setMaxPerObjectLabelCount(1)
            .build()

        objectDetector = ObjectDetection.getClient(options)
    }


    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(
                    cameraExecutor,
                    ImageAnalyzer(
                        object :
                            IImageAnalyzer {
                            override fun updateImage(
                                image: InputImage,
                                imageProxy: ImageProxy
                            ) {
                                processImage(image, imageProxy)
                            }
                        })
                )
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun processImage(
        image: InputImage,
        imageProxy: ImageProxy
    ) {
        objectDetector.process(image).addOnSuccessListener {
            for (detectedObject in it) {
                for (label in detectedObject.labels) {
                    if (label.text == DETECTOR_HAND_LABEL) {
                        enableHandDetected()
                    }
                }
            }
            imageProxy.close()
        }
            .addOnFailureListener {
                disableHandDetected()
                imageProxy.close()
            }
    }

    private fun disableHandDetected() {
        tvIdicator.isEnabled = false
        tvIdicator.text = getString(R.string.searching_palm)
        ivTakePhoto.isEnabled = false
        ivHand.visibility = View.VISIBLE
    }

    private fun enableHandDetected() {
        tvIdicator.isEnabled = true
        tvIdicator.text = getString(R.string.searching_palm_enabled)
        ivTakePhoto.isEnabled = true
        lastDetect = Calendar.getInstance().timeInMillis
        ivHand.visibility = View.INVISIBLE
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }




    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}