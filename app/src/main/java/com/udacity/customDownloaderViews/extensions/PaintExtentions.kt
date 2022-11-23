package com.udacity.customDownloaderViews.extensions

import android.graphics.Paint

class PainterUtils{

    companion object{
        fun initPainterWith(colorToBePainted: Int): Paint {
            return Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = colorToBePainted
            }
        }
    }
}