package com.wsoteam.horoscopes.presentation.onboard.scan

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.ml.ModelQ
import com.wsoteam.horoscopes.presentation.onboard.scan.IImageAnalyzer
import org.tensorflow.lite.support.image.TensorImage

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