package com.udacity.customDownloaderViews.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

fun ValueAnimator.initiateWith(downloadDuration: Long,
                               animatorUpdatesListener: ValueAnimator.AnimatorUpdateListener,
                               animatorProgressListener: Animator.AnimatorListener): ValueAnimator{

    duration = downloadDuration

    interpolator = LinearInterpolator()

    addUpdateListener(animatorUpdatesListener)
    addListener(animatorProgressListener)

    repeatMode = ValueAnimator.REVERSE
    repeatCount = ValueAnimator.INFINITE

    return this
}