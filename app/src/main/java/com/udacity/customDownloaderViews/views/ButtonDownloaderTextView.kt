package com.udacity.customDownloaderViews.views

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.udacity.R
import com.udacity.customDownloaderViews.extensions.PainterUtils

class ButtonDownloaderTextView(resources: Resources,
                               private val buttonTextColor: Int,
                               var textViewSize: Float) {

    private var btnTextLabel: String = resources.getString(R.string.btn_download_initial_state)


    private val textViewPainter = PainterUtils.initPainterWith(buttonTextColor).apply {
        textSize = textViewSize
        textAlign = Paint.Align.LEFT
    }

    private var textViewContainer = Rect()


    fun updateButtonTextLabel(btnTextLabel: String){
        this.btnTextLabel = btnTextLabel
    }

    fun drawMe(canvas: Canvas){
        canvas.drawText(
            btnTextLabel,
            (canvas.width - textViewPainter.measureText(btnTextLabel)) / 2f,
            (canvas.height - (textViewPainter.ascent() + textViewPainter.descent())) / 2f,
            textViewPainter
        )

        textViewPainter.getTextBounds(btnTextLabel,0,btnTextLabel.length,textViewContainer)
    }

    private fun getCurrentTextContainerWidth(): Float{
        return textViewPainter.measureText(btnTextLabel)
    }

    private fun getTextViewContainerHeight(): Float{
        return textViewContainer.height().toFloat()
    }

    fun getExactEndPositionOfTextView(canvas: Canvas): Pair<Float,Float>{
        val translationDx = (canvas.width + getCurrentTextContainerWidth() + getTextViewContainerHeight()) / 2f
        val translationDy = canvas.height / 2f - getTextViewContainerHeight() / 2
        return Pair(translationDx,translationDy)
    }
}