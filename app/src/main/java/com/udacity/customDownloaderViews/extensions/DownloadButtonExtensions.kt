package com.udacity.customDownloaderViews.extensions

import android.animation.ValueAnimator
import android.content.res.Resources
import android.view.View
import com.udacity.customDownloaderViews.model.ButtonState
import com.udacity.R

fun ButtonState.textLabel(resources: Resources): String {
    return when (this) {
        ButtonState.Clicked -> resources.getString(R.string.btn_download_clicked_state)

        ButtonState.Loading -> resources.getString(R.string.btn_download_downloading_state)
        ButtonState.Pending -> resources.getString(R.string.btn_download_downloading_state)
        ButtonState.Paused -> resources.getString(R.string.btn_download_downloading_state)

        ButtonState.Completed -> resources.getString(R.string.btn_download_initial_state)
        ButtonState.Failed -> resources.getString(R.string.btn_download_initial_state)
    }
}

fun ButtonState.updateDownloadState(buttonView: View, valueAnimator: ValueAnimator){
    when(this){
        ButtonState.Clicked -> {
            updateViewState(buttonView) {  }
        }

        ButtonState.Loading -> {
            updateViewState(buttonView) {
                valueAnimator.start()
            }
        }

        ButtonState.Pending -> {
            updateViewState(buttonView) {
                valueAnimator.start()
            }
        }

        ButtonState.Paused -> {
            updateViewState(buttonView) {
                valueAnimator.start()
            }
        }

        ButtonState.Completed -> {
            updateViewState(buttonView) {  valueAnimator.cancel() }
        }

        ButtonState.Failed -> {
            updateViewState(buttonView) {  valueAnimator.cancel() }
        }
    }
}

fun updateViewState(view: View, uiUpdate: () -> Unit){
    uiUpdate.invoke()
    view.invalidate()
}