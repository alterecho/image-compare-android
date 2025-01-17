package com.vjc.imagecompare.extensions

import android.graphics.PointF
import android.view.View
import android.view.ViewGroup

fun View.center() {
    if (this.parent is ViewGroup) {
        val parentViewGroup = this.parent as ViewGroup
        this.x = parentViewGroup.width * 0.5f - this.width * 0.5f
        this.y = parentViewGroup.height * 0.5f - this.height * 0.5f
    }
}

fun View.removeFromParent(): Boolean {
    if (this.parent == null || this.parent !is ViewGroup) {
        return false
    }

    val viewGroup = this.parent as ViewGroup
    viewGroup.removeView(this)

    return true
}