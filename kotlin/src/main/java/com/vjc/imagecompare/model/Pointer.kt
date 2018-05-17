package com.vjc.imagecompare.model

import android.graphics.Point
import android.graphics.PointF
import android.view.MotionEvent

/** An abstraction of the concept of pointers for multi-touch.
 * 'id' is set initially based on the MotionEvenet passed in the constructor.
 * Updates the index when the associated MotionEvent is update (using the 'motionEvent' property. Gets the index based on the initially set ID).
 */
class Pointer constructor(motionEvent: MotionEvent) {

    val point: PointF
        get() = _point

    val ID: Int
        get() = _ID

    override fun equals(other: Any?): Boolean {
        (other as? Pointer)?.let {
            return  other.ID == _ID
        }
        return false
    }

    fun equals(event: MotionEvent): Boolean {
        val index = event.actionIndex
        val id = event.getPointerId(index)
        if (id == _ID) {
            return true
        }
        return false
    }

    override fun toString(): String = "id: $_ID, index: $_index, point:$_point"
    var _index: Int = -1
    val _ID: Int


    private var _motionEvent: MotionEvent
    private var _point: PointF

    init {
        _motionEvent = motionEvent
        _index = motionEvent.actionIndex
        _ID = motionEvent.getPointerId(_index)
        _point = PointF(motionEvent.getX(_index), motionEvent.getY(_index))
    }

    var motionEvent: MotionEvent = _motionEvent
        get() = _motionEvent
        set(value) {
            _motionEvent = value
            _index = field.findPointerIndex(_ID)
            println(this)
            if (_index < 0) {
                _point = PointF()
            } else {
                _point = PointF(_motionEvent.getX(_index), _motionEvent.getY(_index))
            }

        }

}