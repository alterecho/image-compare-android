package com.vjc.imagecompare.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.util.Size
import android.util.SizeF
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.vjc.imagecompare.extensions.center

class ImageView constructor(ctx: Context) : ImageView(ctx) {

    public var bitmap: Bitmap?
        get() = if (this.drawable != null) (this.drawable as BitmapDrawable).bitmap else null
        set(value){
            this.setBitmap(value, null)
        }

    var position
        get() = PointF(this.x + width * 0.5f, this.y + height * 0.5f)
        set(value) {
            val size = Size(width, height)
            val p = this.getCorrectedPosition(value)

            this.x = p.x
            this.y = p.y

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


    /** sets the bitmap by applying rotation based on the orientation in the ExifInterface */
    fun setBitmap(bitmap: Bitmap?, exifInterface: ExifInterface?) {
        var bitmap = bitmap

        if (bitmap != null) {
            exifInterface?.let {
                var orientation = it.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
                var width = bitmap!!.width
                var height = bitmap!!.height
                var angle = 0.0f

                when (orientation) {
                    ExifInterface.ORIENTATION_NORMAL -> {
                        width = bitmap!!.height
                        height = bitmap!!.width
                        angle = 0.0f
                    }
                    ExifInterface.ORIENTATION_ROTATE_90 -> {
                        width = bitmap!!.width
                        height = bitmap!!.height
                        angle = 90.0f
                    }

                    ExifInterface.ORIENTATION_ROTATE_270 -> {
                        width = bitmap!!.width
                        height = bitmap!!.height
                        angle = 270.0f
                    }

                    ExifInterface.ORIENTATION_ROTATE_180 -> {
                        width = bitmap!!.height
                        height = bitmap!!.width
                        angle = 180.0f
                    }

                    else -> {
                        width = bitmap!!.width
                        height = bitmap!!.height
                        angle = 0.0f
                    }
                }

                val matrix = Matrix()
                matrix.setRotate(angle)

                try {
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
                } catch (e: Exception) {
                    val toast = Toast.makeText(this.context, "Unable to load image", 5)
                    toast.show()
                }

            }
        }

        this.setImageBitmap(bitmap)
        this.layoutParams.width = 0
        this.layoutParams.height = 0
        bitmap?.let {
            this.layoutParams.width = it.width
            this.layoutParams.height = it.height
        }
        this.requestLayout()

    }

    fun setBitmap(uri: Uri?, exifInterface: ExifInterface? = null) {
        var bitmap: Bitmap? = null

        uri?.let {
            val pfd = this.context.contentResolver.openFileDescriptor(it, "r")
            val fd = pfd.fileDescriptor
            bitmap = BitmapFactory.decodeFileDescriptor(fd)
            pfd.close()
        }

        this.setBitmap(bitmap, exifInterface)
    }

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

    fun reset() {
        this.toggleImageSize()
        this.center()
        this.rotation = 0.0f
    }

    /** returns a point that will restrict the ImageView(this) from moving out of screen */
    private fun getCorrectedPosition(point: PointF): PointF {
        var x = point.x - size.width * 0.5f
        var y = point.y - size.height * 0.5f

        // margin boundary
        val margin = 10.0f

        if (this.parent is ViewGroup) {
            val viewGroup =  this.parent as ViewGroup
            if (x + size.width < margin) {
                x = -size.width + margin
            } else if (x > viewGroup.width - margin) {
                x = viewGroup.width - margin
            }

            if (y + size.height < margin) {
                y = -size.height + margin
            } else if (y > viewGroup.height - margin) {
                y = viewGroup.height - margin
            }
        }

        return PointF(x, y)
    }
}