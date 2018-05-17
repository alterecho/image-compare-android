package com.vjc.imagecompare

import android.graphics.PointF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.vjc.imagecompare.extensions.bySubtracting
import com.vjc.imagecompare.model.Pointer
import java.lang.ref.WeakReference
import kotlin.math.atan2

class RotationGestureDetector {

    interface OnRotationGestureListener {
        fun onRotationBegin(detector: RotationGestureDetector)

        fun onRotationChanged(detector: RotationGestureDetector)

        fun onRotationEnd(detector: RotationGestureDetector)
    }

    val rotation: Float
        get() = _rotation

    var delegate: OnRotationGestureListener?
        get() = _delegateRef?.get()
        set(value) {
            if (value != null) {
                _delegateRef = WeakReference(value)
            } else {
                _delegateRef = null
            }
        }

    fun onTouchEvent(event: MotionEvent?): Boolean {

        println("p1: $_pointer1")
        println("p2: $_pointer2")

        event?.let {
            var p1 = PointF(it.x, it.y);
            var p2: PointF = PointF()
            var angle = 0.0f

            _pointer1?.motionEvent = event
            _pointer2?.motionEvent = event

            _pointer1?.let {
                p1 = it.point
            }

            _pointer2?.let {
                p2 = it.point
                angle = atan2(p2.y - p1.y, p2.x - p1.x)
            }

            println("p1: $_pointer1")
            println("p2: $_pointer2")

            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    _pointer1 = Pointer(event)

                    return true
                }

                MotionEvent.ACTION_POINTER_DOWN -> {
                    _pointer2 = Pointer(event)
                    p2 = _pointer2!!.point
                    angle = atan2(p2.y - p1.y, p2.x - p1.x)
                    _startAngle = angle

                    println("startangle set to $_startAngle (between $p2, $p1)")
                    _delegateRef?.get()?.onRotationBegin(this)


                    return true
                }

                MotionEvent.ACTION_MOVE -> {

                    if (_pointer2 != null) {
                        _rotation = Math.toDegrees((angle - _startAngle).toDouble()).toFloat()
//                        println("angle: ${Math.toDegrees(angle.toDouble())}, startAngle: ${Math.toDegrees(_startAngle.toDouble())}, change: ${_rotation}")

                        _delegateRef?.get()?.onRotationChanged(this)
                        return true

                    } else {

                    }

                }

                MotionEvent.ACTION_POINTER_UP -> {
                    _pointer2 = null
                }

                MotionEvent.ACTION_UP -> {
                    _pointer1 = null
                    _delegateRef?.get()?.onRotationEnd(this)
                }

                MotionEvent.ACTION_CANCEL -> {
                    _pointer1 = null
                    _pointer2 = null

                    _delegateRef?.get()?.onRotationEnd(this)
                }

                else -> {

                }
            }
        }

        return false
    }


    /** touch identifier for first and second touch (to differentiate movement and rotation) */
    private var _pointer1: Pointer? = null
    private var _pointer2: Pointer? = null

    /** the angle w.r.t _imageView position (center), when the rotation starts */
    private var _startAngle = 0.0f

    /** backing var for change in 'rotation' */
    private var _rotation = 0.0f

    /** backing for 'delegate' */
    private var _delegateRef: WeakReference<OnRotationGestureListener>? = null
}