package com.udacity.customDownloaderViews.views

import android.graphics.Canvas
import android.graphics.Rect
import com.udacity.customDownloaderViews.extensions.PainterUtils

class DownloaderButtonAnimView (animationProgressBackgroundColor: Int){

    private val downloaderProgressPainter = PainterUtils.initPainterWith(animationProgressBackgroundColor)
    private var downloaderProgressPainterContainer = Rect()


    fun drawMe(canvas: Canvas,currentDownloaderProgress: Float){
        downloaderProgressPainterContainer.set(0, 0, (canvas.width * currentDownloaderProgress).toInt(), canvas.height)

        canvas.drawRect(downloaderProgressPainterContainer , downloaderProgressPainter)
    }
}