package com.udacity.customDownloaderViews.views

import android.graphics.Canvas
import android.graphics.Rect
import com.udacity.customDownloaderViews.extensions.PainterUtils

class ButtonDownloaderContainer(buttonBackgroundColor: Int) {

    private var downloadButtonContainer = Rect()
    private val downloadButtonPainter = PainterUtils.initPainterWith(buttonBackgroundColor)



    fun drawMe(canvas: Canvas){
        downloadButtonContainer.set(0,0,canvas.width,canvas.height)
        canvas.drawRect(downloadButtonContainer,downloadButtonPainter)
    }

}