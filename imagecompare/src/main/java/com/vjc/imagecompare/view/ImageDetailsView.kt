package com.vjc.imagecompare.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.vjc.imagecompare.R
import com.vjc.imagecompare.extensions.center

class ImageDetailsView : LinearLayout {

    init {
        val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_details, this, true)
//        View.inflate(this.context, R.layout.view_details, this)
    }

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

//    val isShown: Boolean
//    get() = this.parent != null

    public fun showIn(viewGroup: ViewGroup) {
        viewGroup.removeView(this)
        viewGroup.addView(this)
        val layoutParam = this.layoutParams
        layoutParam.width = (viewGroup.width * 0.75f).toInt()
        layoutParam.height = (viewGroup.height * 0.75f).toInt()
        viewGroup.requestLayout()

        this.post( {
            //TODO: thread safety
            this.center()
        })
    }

    fun hide() {
        if (this.parent !is ViewGroup) {
            return
        }

        (this.parent as ViewGroup).removeView(this)
    }

}