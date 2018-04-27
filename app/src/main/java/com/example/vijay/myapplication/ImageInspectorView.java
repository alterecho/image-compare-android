package com.example.vijay.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/** A view managed by each ImagenspectorFragment.
 * Manages an ImageView, and allows it to be zoomed and rotated.
 * The bitmap for the ImageView has to using setBitMap method.
 * */
public class ImageInspectorView extends RelativeLayout implements View.OnTouchListener {

    public ImageInspectorView(Context context) {
        super(context);
        init();
    }

    public ImageInspectorView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        init();
    }



    /** sets the image of the ImageView this view manages */
    public void setBitmap(Bitmap bm) {
        _imageView.setImageBitmap(bm);

        if (bm == null) {

            ImageView imageView = new ImageView(this.getContext());
            imageView = _imageView;
            imageView.setBackgroundColor(Color.RED);
//            this.addView(imageView);

            imageView.getLayoutParams().width = 100;
            imageView.getLayoutParams().height = 100;

        } else {
            int width = bm.getWidth();
            int height = bm.getHeight();

            _imageView.getLayoutParams().width = width;
            _imageView.getLayoutParams().height = height;
        }
        _imageView.requestLayout();

        centerImage();
    }


    public void centerImage() {
        _imageView.setX(this.getWidth() * 0.5f - _imageView.getWidth() * 0.5f);
        _imageView.setY(this.getHeight() * 0.5f - _imageView.getHeight() * 0.5f);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        _gestureDetector.onTouchEvent(event);
        _scaleGestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(null, "ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(null, "ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.d(null, "ACTION_UP");
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float touch_x = event.getX();
        float touch_y = event.getY();

        int width_container = this.getWidth();
        int height_container = this.getHeight();

        int width = _imageView.getWidth();
        int height = _imageView.getHeight();

        /// the new values for the x and y of the _imageView
        float x = touch_x - width * 0.5f;
        float y = touch_y - height * 0.5f;


        // restrict the _imageView within bounds
//        if (x + width > width_container) {
//            x = width_container - width;
//        } else if (x < 0.0f) {
//            x = 0.0f;
//        }
//
//        if (y + height > height_container) {
//            y = height_container - height;
//        } else if (y < 0.0f) {
//            y = 0.0f;
//        }





        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN:
                Log.d("ot", "ACTION_UP");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("ot", "ACTION_UP");
                _imageView.setX(x);
                _imageView.setY(y);
                break;

            case MotionEvent.ACTION_UP:
                Log.d("ot", "ACTION_UP");
                break;

            default:
                break;
        }
        return false;
    }

    private void init() {
        if (_imageView == null) {
            _imageView = new ImageView(getContext());
            _imageView.setBackgroundColor(Color.RED);
            this.addView(_imageView);
        }

        if (_gestureDetector == null) {
            _gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.d(null, "double tap");
                    toggleImageZoom();
                    return super.onDoubleTap(e);
                }
            });
        }

        if (_scaleGestureDetector == null) {
            _scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {

                @Override
                public boolean onScaleBegin(ScaleGestureDetector detector) {
                    return super.onScaleBegin(detector);
                }

                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    Log.d(null, "scale");
                    return super.onScale(detector);
                }

                @Override
                public void onScaleEnd(ScaleGestureDetector detector) {
                    super.onScaleEnd(detector);
                }
            });
        };

        this.setOnTouchListener(this);

        Random rnd = new Random();
        setBackgroundColor(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
    }




    /** ImageView used to display the selected image */
    private ImageView       _imageView;

    /** for detecting double taps */
    private GestureDetector         _gestureDetector;

    /** for detecting pinch gesture */
    private ScaleGestureDetector    _scaleGestureDetector;


    /** toggles the _imageView's size between original size of the Bitmap and size that fits within this view */
    private void toggleImageZoom() {

        if (_imageView == null) {
            return;
        }

        float width_bitmap = 100.0f;
        float height_bitmap = 100.0f;
//        Bitmap bm = ((BitmapDrawable)_imageView.getDrawable()).getBitmap();
//        if (bm == null) {
//            return;
//        }
//
//        float width_bitmap = bm.getWidth();
//        float height_bitmap = bm.getHeight();

        float width_container = this.getWidth();
        float height_container = this.getHeight();

        float width = _imageView.getWidth();
        float height = _imageView.getHeight();

        ViewGroup.LayoutParams params = _imageView.getLayoutParams();

        if (width != width_bitmap || height != height_bitmap) {
            width = width_bitmap;
            height = height_bitmap;
        } else {
            width = (width / height) * height_container;
            height = height_container;
        }

        params.width = (int)width;
        params.height = (int)height;

        _imageView.setX(width_container * 0.5f - width * 0.5f);
        _imageView.setY(height_container * 0.5f - height * 0.5f);

        _imageView.requestLayout();
    }

}
