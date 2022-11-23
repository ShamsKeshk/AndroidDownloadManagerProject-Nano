package com.udacity.customDownloaderViews.views

import android.graphics.Canvas
import android.graphics.Paint
import com.udacity.customDownloaderViews.extensions.PainterUtils

class DownloadWheelView(animationProgressWheelColor: Int) {

    private val  downloaderWheel = PainterUtils.initPainterWith(animationProgressWheelColor).apply {
        textAlign = Paint.Align.RIGHT
    }

    fun drawMe(canvas: Canvas, currentDownloaderProgress: Float,exactPositionOfDownloaderWheel: Pair<Float,Float>){
        canvas.translate(exactPositionOfDownloaderWheel.first, exactPositionOfDownloaderWheel.second)

        canvas.drawArc(
            0f,
            0f,
            45f,
            45f,
            0f,
            360f * currentDownloaderProgress,
            true,
            downloaderWheel
        )
    }
}