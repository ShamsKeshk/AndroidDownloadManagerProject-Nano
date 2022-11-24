package com.udacity.customDownloaderViews

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.udacity.customDownloaderViews.model.ButtonState
import com.udacity.R
import com.udacity.customDownloaderViews.extensions.*
import com.udacity.customDownloaderViews.views.ButtonDownloaderContainer
import com.udacity.customDownloaderViews.views.ButtonDownloaderTextView
import com.udacity.customDownloaderViews.views.DownloadWheelView
import com.udacity.customDownloaderViews.views.DownloaderButtonAnimView
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    //============================================//
    private var buttonTextColor: Int = 0
    private var buttonBackgroundColor: Int = 0
    private var animationProgressBackgroundColor: Int = 0
    private var animationProgressWheelColor: Int = 0
    private var tvSize = 0f
    //============================================//

    //============================================//
    private val downloadingTimeDurationInMill = 2000L

    private var currentDownloaderProgress = 0f
    //============================================//

    init {
        context.withStyledAttributes(attributeSet, R.styleable.LoadingButton) {
            buttonTextColor = getColor(R.styleable.LoadingButton_buttonTextColor, 0)
            buttonBackgroundColor = getColor(R.styleable.LoadingButton_buttonBackgroundColor, 0)
            animationProgressBackgroundColor = getColor(R.styleable.LoadingButton_animationProgressBackgroundColor,0)
            animationProgressWheelColor = getColor(R.styleable.LoadingButton_animationProgressWheelColor, 0)
            tvSize = getDimension(R.styleable.TextAppearance_android_textSize, 0f)
        }
    }

    private val animatorDownloadListener = object: AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            this@LoadingButton.isEnabled  = false
        }

        override fun onAnimationEnd(animation: Animator) {
            this@LoadingButton.isEnabled  = true
        }
    }

    private var downloaderAnimator = initDownloaderAnimator(downloadingTimeDurationInMill)

    var downloaderButtonState: ButtonState by Delegates.observable(ButtonState.Completed) { _, _, new ->
        buttonDownloaderTextView.updateButtonTextLabel(new.textLabel(resources))
        if (new is ButtonState.Loading){
            ButtonState.Loading.downloadingTimeDurationInMill = downloadingTimeDurationInMill
            downloaderAnimator = initDownloaderAnimator(downloadingTimeDurationInMill)
        }
        new.updateDownloadState(this,downloaderAnimator)
    }

    private val buttonDownloaderContainer = ButtonDownloaderContainer(buttonBackgroundColor)

    private val  downloaderButtonAnimView = DownloaderButtonAnimView(animationProgressBackgroundColor)

    private val  downloaderWheelView = DownloadWheelView(animationProgressWheelColor)

    private val buttonDownloaderTextView = ButtonDownloaderTextView(resources,
        buttonTextColor,tvSize)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when(downloaderButtonState){
            ButtonState.Loading -> drawDownloadingViews(canvas)
            else -> drawInitialState(canvas)
        }
    }

    private fun drawInitialState(canvas: Canvas){
        buttonDownloaderContainer.drawMe(canvas)

        buttonDownloaderTextView.drawMe(canvas)
    }

    private fun drawDownloadingViews(canvas: Canvas){
        buttonDownloaderContainer.drawMe(canvas)

        downloaderButtonAnimView.drawMe(canvas,currentDownloaderProgress)

        buttonDownloaderTextView.drawMe(canvas)

        downloaderWheelView.drawMe(canvas,currentDownloaderProgress,buttonDownloaderTextView.getExactEndPositionOfTextView(canvas))
    }

    private fun initDownloaderAnimator(downloadingTimeDurationInMill: Long): ValueAnimator{
        return ValueAnimator.ofFloat(0f,1f)
            .initiateWith(downloadingTimeDurationInMill,
                { updateViewState(this) { currentDownloaderProgress = it.animatedValue as Float} }
                ,animatorDownloadListener)
    }
}