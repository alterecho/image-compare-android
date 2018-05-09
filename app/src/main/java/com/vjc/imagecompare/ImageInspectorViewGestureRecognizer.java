package com.vjc.imagecompare;

import android.graphics.PointF;
import android.util.Log;
import android.util.SizeF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

//public class ImageInspectorViewGestureHandler {

//    public ImageInspectorViewGestureHandler() {
//        _gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                if (_listenerRef == null) {
//                    return false;
//                }
//                _listenerRef.get().onDoubleTap();
//                _imageView.setRotation(0.0f);
//
//                return super.onDoubleTap(e);
//            }
//        });
//
//        if (_scaleGestureDetector == null) {
//            _scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
//
//                @Override
//                public boolean onScaleBegin(ScaleGestureDetector detector) {
//                    if (_listenerRef == null) {
//                        return false;
//                    }
//
//                    _listenerRef.get().onScaleBegin(detector.getScaleFactor());
//
//
//
//                    return super.onScaleBegin(detector);
//                }
//
//                @Override
//                public boolean onScale(ScaleGestureDetector detector) {
//
//
//                    return super.onScale(detector);
//                }
//
//                @Override
//                public void onScaleEnd(ScaleGestureDetector detector) {
//                    super.onScaleEnd(detector);
//                }
//
//
//            });
//        };
//    }
//
//
//    public boolean onTouchEvent(MotionEvent event) {
//        if (_listenerRef == null) {
//            return false;
//        }
//
//        _scaleGestureDetector.onTouchEvent(event);
//        _gestureDetector.onTouchEvent(event);
//
//        float x = event.getX(), y = event.getY();
//        float x2 = 0.0f, y2 = 0.0f;
//
//        // * index and pointer index of the current event;
//        int pointerIDX = event.getActionIndex();
//        int pointerID = event.getPointerId(pointerIDX);
//
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.d("otv", "ACTION_DOWN");
//
//                pointer1IDX = pointerIDX;
//                pointer1ID = pointerID;
//
//
//                _touch_began = new PointF(x, y);
//                PointF imageViewPos = this.getImageViewPosition();
//                _touch_delta = new SizeF(
//                        event.getX() - imageViewPos.x,
//                        event.getY() - imageViewPos.y
//                );
//                return true;
//
//            case MotionEvent.ACTION_POINTER_DOWN:
//                pointer2IDX = pointerIDX;
//                pointer2IDX = pointerID;
//
//                x2 = event.getX(pointer2IDX);
//                y2 = event.getY(pointer2IDX);
//                Log.d("otv", String.format("ACTION_POINTER_DOWN p1:(%f, %f), p2:(%f, %f)", x, y, x2, y2));
//
//                _angle_initial = Math.atan2(y2 - y, x2 - x);
//
//                _angle_initial_imageView = Math.toRadians(_imageView.getRotation());
//                Log.d("ang initial", "" + Math.toDegrees(_angle_initial));
//
//
//                return true;
//
////                break;
//
//            case MotionEvent.ACTION_MOVE:
////                Log.d("otv", "ACTION_MOVE");
//                if (_imageView != null && pointer2IDX != -1) {
//                    x2 = event.getX(pointer2IDX);
//                    y2 = event.getY(pointer2IDX);
//                    double angle = Math.atan2(y2 - y,(x2 - x));
//                    Log.d("rota", String.format("imageRot:%f, angle: %f", Math.toDegrees(_imageView.getRotation()), Math.toDegrees(angle)));
//                    _imageView.setRotation((float)Math.toDegrees(_angle_initial_imageView + (angle - _angle_initial)));
//                    return true;
//                } else {
//
//                }
//
//                if (pointerID == pointer1ID) {
//                    _imageView.setPosition(x - _touch_delta.getWidth(), y - _touch_delta.getHeight());
//                }
//
//
//                break;
//
//            case MotionEvent.ACTION_UP:
//                pointer1ID = pointer1IDX = -1;
//
//            case MotionEvent.ACTION_POINTER_UP:
//                pointer2ID = pointer2IDX = -1;
//                break;
//
//            case MotionEvent.ACTION_CANCEL:
//                Log.d("otv", "ACTION_UP/CANCEL");
//                pointer1ID = pointer1IDX = -1;
//                pointer2ID = pointer2IDX = -1;
//                break;
//
//            default:
//                break;
//        }
//    }
//
//
//    /** for detecting double taps */
//    private GestureDetector _gestureDetector;
//
//    /** for detecting pinch gesture */
//    private ScaleGestureDetector _scaleGestureDetector;
//
//    /** delegate to report events */
//    private WeakReference<Listener>     _listenerRef;
//
//    private PointF      _touch_began, _touch_delta;
//
//    private int    pointer1ID = -1, pointer1IDX = -1, pointer2ID = -1, pointer2IDX = -1;
//}
