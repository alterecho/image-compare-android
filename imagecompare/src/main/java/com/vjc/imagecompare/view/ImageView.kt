package com.vjc.imagecompare.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.BitmapDrawable
import android.util.Size
import android.util.SizeF
import android.view.ViewGroup
import android.widget.ImageView

class ImageView constructor(ctx: Context) : ImageView(ctx) {

    public var bitmap: Bitmap? = null
        get() = (this.drawable as BitmapDrawable).bitmap
        set(value){
            field = value
            this.setImageBitmap(field)
            this.layoutParams.width = 100
            this.layoutParams.height = 100
            field?.let {
                this.layoutParams.width = it.width
                this.layoutParams.height = it.height
            }
            this.requestLayout()
        }

    var position
        get() = PointF(this.x + width * 0.5f, this.y + height * 0.5f)
        set(value) {
            val size = Size(width, height)

            this.x = value.x - size.width * 0.5f
            this.y = value.y - size.height * 0.5f
        }

    var size: SizeF
    get() = SizeF(this.width.toFloat(), this.height.toFloat())
    set(value) {
        val layoutParam = this.layoutParams
        layoutParams.width = value.width.toInt()
        layoutParams.height = value.height.toInt()
        this.requestLayout()
    }

//    /** centers in parent ViewGroup */
//    fun center() {
//
//    }

    /** switches the _imageView size between the size of bitmap and size that fits in view */
    fun toggleImageSize() {
        if (this.parent is ViewGroup) {
            val parentViwGroup = this.parent as ViewGroup
            bitmap?.let {
                var size = this.size
                val size_bitmap = SizeF(it.width.toFloat(), it.height.toFloat())
                if (size.width != size_bitmap.width || size.height != size_bitmap.height) {
                    size = SizeF(size_bitmap.width, size_bitmap.height)
                } else {
                    size = SizeF(size_bitmap.width / size_bitmap.height * parentViwGroup.height, parentViwGroup.height.toFloat())
                }

                this.size = size
            }

        }

    }
}