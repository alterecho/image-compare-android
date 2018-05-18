package com.vjc.imagecompare

import android.graphics.Point
import android.graphics.PointF
import android.view.MotionEvent
import java.lang.ref.WeakReference

class PanGestureDetector {

    interface Listener {
        fun touchBegin(gesture: PanGestureDetector)
        fun touchMove(gesture: PanGestureDetector)
        fun touchEnd(gesture: PanGestureDetector)
    }

    var delegate: Listener?
        get() = _delegateRef?.get()
        set(value) { if (value != null) _delegateRef = WeakReference(value) else _delegateRef = null}

    val location: PointF
        get() = _loc

    val vector: Vec2
        get() = _vec

    fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            _loc = PointF(it.x, it.y)

            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    _pnt_begin = PointF(_loc.x, _loc.y)
                    _delegateRef?.get()?.touchBegin(this)
                    return true
                }

                MotionEvent.ACTION_POINTER_DOWN -> {
                    return false
                }

                MotionEvent.ACTION_MOVE -> {
                    _vec = Vec2(_pnt_begin.x - _loc.x, _loc.y - _pnt_begin.y)
                    _delegateRef?.get()?.touchMove(this)
                    return true
                }

                MotionEvent.ACTION_UP -> {
                    _delegateRef?.get()?.touchEnd(this)
                    _pnt_begin = PointF()
                    _vec = Vec2()
                }

                MotionEvent.ACTION_CANCEL -> {
                    _pnt_begin = PointF()
                    _vec = Vec2()
                }

                else -> {

                }
            }
        }
        return false
    }


    private var _loc: PointF = PointF()
    private var _vec: Vec2 = Vec2()
    private var _delegateRef: WeakReference<Listener>? = null

    /** point where 1st touch began */
    private var _pnt_begin = PointF()
}