package com.udacity.customDownloaderViews.model


sealed class ButtonState() {
    object Clicked : ButtonState()
    object Loading: ButtonState(){
        var downloadingTimeDurationInMill: Long = 0L
    }
    object Completed : ButtonState()
    object Failed : ButtonState()
    object Paused : ButtonState()
    object Pending : ButtonState()
}