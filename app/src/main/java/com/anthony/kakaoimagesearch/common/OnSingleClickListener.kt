package com.anthony.kakaoimagesearch.common

import android.os.SystemClock
import android.view.View

abstract class OnSingleClickListener : View.OnClickListener {
    private var mMinClickInterval = 300L
    private var mLastClickTime: Long = 0

    constructor()

    constructor(clickInterval: Long) {
        mMinClickInterval = clickInterval
    }

    abstract fun onSingleClick(v: View)

    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        if (elapsedTime <= mMinClickInterval) {
            return
        }
        onSingleClick(v)
    }

    fun setClickInterval(interval: Long) {
        mMinClickInterval = interval
    }
}