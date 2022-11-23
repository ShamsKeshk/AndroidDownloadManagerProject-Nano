package com.udacity.framework.model

class SingleEvent<T> constructor(var content: T? = null){
    var isHandled = false
        private set

    val contentIfNotHandled: T?
        get() = if (isHandled) {
            null
        } else {
            setHasBeenHandled()
            content
        }

    private fun setHasBeenHandled() {
        isHandled = true
    }
}