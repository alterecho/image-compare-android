package com.vjc.imagecompare.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PointF
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.LruCache
import android.util.Size
import android.util.SizeF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.FrameLayout
import com.vjc.imagecompare.extensions.*
import com.vjc.imagecompare.model.Pointer
import kotlin.math.atan2

class ImageInspectorView : FrameLayout, ScaleGestureDetector.OnScaleGestureListener {

    constructor(ctx: Context) : super(ctx) {
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)


    fun setBitmapUri(uri: Uri?, exifInterface: ExifInterface? = null) {
        _imageView.setBitmap(uri, exifInterface)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superPacelable = super.onSaveInstanceState()
        val bundle = Bundle()

        val bitmap = _imageView.bitmap

        bitmap?.let {
            bundle.putParcelable(KEY_SUPER_PARCELABLE, superPacelable)
            bundle.putFloat(KEY_POSITION_X, _imageView.position.x)
            bundle.putFloat(KEY_POSITION_Y, _imageView.position.y)
            bundle.putFloat(KEY_SIZE_WIDTH, _imageView.size.width)
            bundle.putFloat(KEY_SIZE_HEIGHT, _imageView.size.height)
            bundle.putFloat(KEY_ANGLE, _imageView.rotation)

            _bitmapCache.put(KEY_BITMAP, it)
        }

        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {

        var state = state

        if (state is Bundle) {
            val bundle = state as Bundle
            state = bundle.getParcelable<Parcelable>(KEY_SUPER_PARCELABLE)

            val bitmap = _bitmapCache.get(KEY_BITMAP)

            bitmap?.let {
                val position = PointF(bundle.getFloat(KEY_POSITION_X, 0.0f), bundle.getFloat(KEY_POSITION_Y, 0.0f))
                val size = SizeF(bundle.getFloat(KEY_SIZE_WIDTH, 0.0f), bundle.getFloat(KEY_SIZE_HEIGHT, 0.0f))
                val angle = bundle.getFloat(KEY_ANGLE)
                _imageView.bitmap = bitmap

                _imageView.position = position
                _imageView.size = size
                _imageView.post {
                    _imageView.position = position
                    _imageView.rotation = angle
                }
            }




        }

        super.onRestoreInstanceState(state)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        _gestureDetector.onTouchEvent(event)
        if (_scaleGestureDetector.onTouchEvent(event)) {
//            return true
        }

        event?.let {
            val p = PointF(it.x, it.y);
            var p2: PointF
            var angle = 0.0f
            val pointer2 = _pointer2
            if (pointer2 != null) {
                p2 = pointer2.point(event)
                angle = atan2(p2.y - _imageView.position.y, p2.x - _imageView.position.x)
            }

            val pointer = Pointer(it)

            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    _touchPoint_down = p
                    _touchPoint_offset = p.bySubtracting(_imageView.position)

                    _pointer1 = pointer

                    return true
                }

                MotionEvent.ACTION_POINTER_DOWN -> {
                    _pointer2 = pointer
                    _startAngle_imageView = _imageView.rotation
                    _startAngle = angle

                    return true
                }

                MotionEvent.ACTION_MOVE -> {
                    if (pointer.id == pointer.id) {
                        _imageView.position = p.bySubtracting(_touchPoint_offset)
                    } else {

                    }

                    if (pointer2 != null) {
                        _imageView.rotation = _startAngle_imageView + Math.toDegrees((angle - _startAngle).toDouble()).toFloat();
                    }

                }

                MotionEvent.ACTION_POINTER_UP -> {
                    _pointer2 = null
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    _touchPoint_offset = PointF()
                    _pointer1 = null
                }
            }
        }

        return false
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        _startSize = SizeF(_imageView.width.toFloat(), _imageView.height.toFloat())
        _scaleFactor = 1.0f;
        return true
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        detector?.let {

            _scaleFactor += it.scaleFactor - 1.0f;
            println("${it.scaleFactor}, $_scaleFactor")
            _imageView.layoutParams.height = (_startSize.width * _scaleFactor).toInt()
            _imageView.layoutParams.width = (_startSize.height * _scaleFactor).toInt()
            _imageView.requestLayout()
            return true
        }
        return false
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {

    }





    private val _imageView: ImageView = ImageView(this.context)

    private val _scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(this.context, this)
    /** for double tap */
    private val _gestureDetector: GestureDetector = GestureDetector(this.context, object : GestureDetector.SimpleOnGestureListener() {
        //TODO: move all gestures to a single SimpleOnGestureDetector
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            _imageView.toggleImageSize()
            _imageView.post(object : Runnable {
                override fun run() {
                    _imageView.reset()
                }
            })
            return super.onDoubleTap(e)
        }
    })

    private var _touchPoint_down = PointF(0.0f, 0.0f)
    /** difference between where the the touch began and where _imageView position (center) */
    private var _touchPoint_offset = PointF()

    /** touch identifier for first and second touch (to differentiate movement and rotation) */
    private var _pointer1: Pointer? = null
    private var _pointer2: Pointer? = null

    /** the angle w.r.t _imageView position (center), when the rotation starts */
    private var _startAngle = 0.0f

    /** the angle of the _imageView when thr rotation starts */
    private var _startAngle_imageView = 0.0f

    /** start size of _imageView when scale scale gesture began */
    private var _startSize = SizeF(0.0f, 0.0f)

    /** for pinch zoom */
    private var _scaleFactor = 1.0f;




    init {
        this.addView(_imageView)
        _imageView.setBackgroundColor(Color.RED)
        _imageView.setBitmap(uri = null, exifInterface = null)
    }

    companion object {
        private val _bitmapCache: LruCache<String, Bitmap>
        init {
            val availableCache = Runtime.getRuntime().maxMemory().toInt() / 1024
            _bitmapCache = LruCache(100)
//            _bitmapCache = object : LruCache<String, Bitmap>(availableCache) {
//                override fun sizeOf(key: String?, value: Bitmap?): Int {
//                    value?.let {
//                        return it.byteCount / 1024
//                    }
//                    return 0
//                }
//            }

        }
    }
}

private const val KEY_SUPER_PARCELABLE = "super.parcelable"
private const val KEY_BITMAP = "bitmap"
private const val KEY_POSITION_X = "imageView.position.x"
private const val KEY_POSITION_Y = "imageView.position.y"
private const val KEY_SIZE_WIDTH = "imageView.size.width"
private const val KEY_SIZE_HEIGHT = "imageView.size.height"
private const val KEY_ANGLE = "imageView.angle"