package com.wsoteam.horoscopes.presentation.onboard.scan

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage

class ImageAnalyzer(val iImageAnalyzer: IImageAnalyzer) : ImageAnalysis.Analyzer {


    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null){
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            iImageAnalyzer.updateImage(image, imageProxy)
        }
    }
}