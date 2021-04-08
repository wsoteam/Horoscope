package com.wsoteam.horoscopes.presentation.onboard.scan

import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage

interface IImageAnalyzer {
    fun updateImage(
        image: InputImage,
        imageProxy: ImageProxy
    )
}