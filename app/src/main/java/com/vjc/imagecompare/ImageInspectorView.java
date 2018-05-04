package com.vjc.imagecompare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.util.SizeF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/** A view managed by each ImagenspectorFragment.
 * Manages an ImageView, and allows it to be zoomed and rotated.
 * The bitmap for the ImageView has to using setBitMap method.
 * */
public class ImageInspectorView extends FrameLayout implements View.OnTouchListener {

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

            _imageView.getLayoutParams().width = 100;
            _imageView.getLayoutParams().height = 100;

        } else {
            int width = bm.getWidth();
            int height = bm.getHeight();

            _imageView.getLayoutParams().width = width;
            _imageView.getLayoutParams().height = height;
        }


        // * center the image after it is laid out
        _imageView.post(new Runnable() {
            @Override
            public void run() {
                centerImageView();
            }
        });
    }


    public void centerImageView() {
        this.setImageViewPosition(
                this.getWidth() * 0.5f,
                this.getHeight() * 0.5f
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        _scaleGestureDetector.onTouchEvent(event);
        _gestureDetector.onTouchEvent(event);
//        if (_scaleGestureDetector.onTouchEvent(event) || _gestureDetector.onTouchEvent(event)) {
//            return true;
//        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(null, "ACTION_DOWN");

                return true;

            case MotionEvent.ACTION_MOVE:
                Log.d(null, "ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.d(null, "ACTION_UP");
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float touch_x = event.getX();
        float touch_y = event.getY();

        int width_container = this.getWidth();
        int height_container = this.getHeight();

        /// the new values for the x and y of the _imageView
        float x = touch_x - _touch_delta.getWidth();
        float y = touch_y - _touch_delta.getHeight();


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
                Log.d("ot", "ACTION_DOWN");
                _touch_delta = new SizeF(
                        event.getX() - (_imageView.getX() + _imageView.getWidth() * 0.5f),
                        event.getY() - (_imageView.getY() + _imageView.getHeight() * 0.5f)
                );
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("ot", "ACTION_UP");
                this.setImageViewPosition(x, y);
                break;

            case MotionEvent.ACTION_UP:
                Log.d("ot", "ACTION_UP");
                break;

            default:
                break;
        }
        return false;
    }



    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {

        /*  if there is a bitmap available for the _imageView, use a Bundle object (a subclass of Parcelable) and
            save the Bitmap and _imaeView position in it, and return it */
        BitmapDrawable bitmapDrawable = (BitmapDrawable)_imageView.getDrawable();
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();

            if (bitmap != null) {
                Bundle bundle = new Bundle();
                Parcelable superParcelable = super.onSaveInstanceState();
                bundle.putParcelable(KEY_SUPER_PARCELABLE, superParcelable);
                bundle.putParcelable(KEY_BITMAP, bitmap);
                bundle.putFloat(KEY_POSITION_X, _imageView.getX());
                bundle.putFloat(KEY_POSITION_Y, _imageView.getY());
                return bundle;
            }


        }

        return super.onSaveInstanceState();

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        /* If the state object is an instance of Bundle, extract and set the Bitmap and position for the _imageview */
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle)state;
            state = bundle.getParcelable(KEY_SUPER_PARCELABLE);
            Bitmap bm = bundle.getParcelable(KEY_BITMAP);

            this.setBitmap(bm);

            final Float x = bundle.getFloat(KEY_POSITION_X);
            final Float y = bundle.getFloat(KEY_POSITION_Y);

            /// set the _imageView's position after it has been laid out
            _imageView.post(new Runnable() {
                @Override
                public void run() {
                    setImageViewPosition(x, y);
                }
            });


        }

        super.onRestoreInstanceState(state);
    }




    /** ImageView used to display the selected image */
    private ImageView       _imageView;

    /** for detecting double taps */
    private GestureDetector         _gestureDetector;

    /** for detecting pinch gesture */
    private ScaleGestureDetector    _scaleGestureDetector;

    /** difference between where the touch point began, and the _imageView's center */
    private SizeF          _touch_delta = new SizeF(0.0f, 0.0f);

    /** Keys for restoring state */
    private final static String     KEY_SUPER_PARCELABLE = "super_parcelable";
    private final static String     KEY_POSITION_X = "pos_x";
    private final static String     KEY_POSITION_Y = "pos_y";
    private final static String     KEY_BITMAP = "bitmap";


    private void init() {
        if (_imageView == null) {
            _imageView = new ImageView(getContext());
            _imageView.setBackgroundColor(Color.TRANSPARENT);
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

                    if (_imageView != null) {
                        float scaleFactor = detector.getScaleFactor();
                        _startSize = new SizeF(_imageView.getWidth(), _imageView.getHeight());
                    }

                    return super.onScaleBegin(detector);
                }

                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    Log.d(null, "scale");

                    if (_imageView != null) {
                        float scaleFactor = detector.getScaleFactor();
                        ViewGroup.LayoutParams params = _imageView.getLayoutParams();
                        params.width = (int)(_startSize.getWidth() * scaleFactor);
                        params.height = (int)(_startSize.getHeight() * scaleFactor);
                        _imageView.requestLayout();
                    }

                    return super.onScale(detector);
                }

                @Override
                public void onScaleEnd(ScaleGestureDetector detector) {
                    super.onScaleEnd(detector);
                }

                private SizeF       _startSize;
            });
        };

        this.setOnTouchListener(this);

        Random rnd = new Random();
//        setBackgroundColor(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
    }

    /** Sets the position of Imageview with anchor point at center */
    private void setImageViewPosition(Float x, Float y) {
        PointF p = this.getCorrectedPosition(x, y);
        _imageView.setX(p.x);
        _imageView.setY(p.y);
    }

    /** returns the _imageView position */
    private PointF getImageViewPosition() {
        return new PointF(_imageView.getX(), _imageView.getY());
    }

    /** returns a point that is restricted point for the _imageView (So as to not move the _imageView out of view completely)*/
    private PointF getCorrectedPosition(Float x, Float y) {
        PointF p = new PointF(x, y);

        float border = 10.0f;

        if (p.x + _imageView.getWidth() * 0.5f < border) {
            p.x = border - _imageView.getWidth() * 0.5f;
        } else if (p.x + _imageView.getWidth() * 0.5f > this.getWidth() - border) {
            p.x = this.getWidth() - border - _imageView.getWidth() * 0.5f;
        }

        if (p.y + _imageView.getHeight() * 0.5f < border) {
            p.y = border - _imageView.getHeight() * 0.5f;
        } else if (p.y + _imageView.getHeight() * 0.5f > this.getHeight() - border) {
            p.y = this.getHeight() - border - _imageView.getHeight() * 0.5f;
        }
        return p;
    }


    /** toggles the _imageView's size between original size of the Bitmap and size that fits within this view */
    private void toggleImageZoom() {

        if (_imageView == null || _imageView.getDrawable() == null) {
            return;
        }

        Bitmap bm = ((BitmapDrawable)_imageView.getDrawable()).getBitmap();
        float width_bitmap = bm.getWidth();
        float height_bitmap = bm.getHeight();
//        Bitmap bm = ((BitmapDrawable)_imageView.getDrawable()).getBitmap();
//        if (bm == null) {
//            return;
//        }
//
//        float width_bitmap = bm.getWidth();
//        float height_bitmap = bm.getHeight();

        final float width_container = this.getWidth();
        final float height_container = this.getHeight();



        ViewGroup.LayoutParams params = _imageView.getLayoutParams();
        float width = params.width;
        float height = params.height;

        if (width != width_bitmap || height != height_bitmap) {
            width = width_bitmap;
            height = height_bitmap;
        } else {
            width = (width / height) * height_container;
            height = height_container;
        }

        params.width = (int)width;
        params.height = (int)height;

        _imageView.requestLayout();

        _imageView.post(new Runnable() {
            @Override
            public void run() {
                centerImageView();
            }
        });



    }

}
