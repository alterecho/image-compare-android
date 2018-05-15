package com.vjc.imagecompare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
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

import com.vjc.imagecompare.Model.MetaData;

import java.io.FileDescriptor;
import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/** A view managed by each ImagenspectorFragment.
 * Manages an ImageView, and allows it to be zoomed and rotated.
 * The bitmap for the ImageView has to using setBitMap method.
 * */
public class ImageInspectorView extends FrameLayout {

    public ImageInspectorView(Context context) {
        super(context);
        init();
    }

    public ImageInspectorView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        init();
    }


    //TODO: move to provider!
    public void setBitmapFrom(Uri imageUri, AtomicReference<List<MetaData>> metaDataArrayRef) {
        float bm_angle = 0.0f;
        int bm_width = 0;
        int bm_height = 0;
        int orientation = ExifInterface.ORIENTATION_NORMAL;
        try {

            //* set the MetaData array for the _imageDetailsView
            try {
                AtomicReference<ExifInterface> exifInterfaceAtomicReference = new AtomicReference<>();
                List<MetaData> metaDataArray = MetaData.metaDataArrayFrom(imageUri, getContext(), exifInterfaceAtomicReference) ;
                if (metaDataArrayRef != null) {
                    metaDataArrayRef.set(metaDataArray);
                }
                orientation = exifInterfaceAtomicReference.get().getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


            } catch (MetaData.MetaDataException e){
                e.printStackTrace();
            }

            //* retrieve and set the bitmap for the _imageIspectorView
//            Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

            ParcelFileDescriptor pfd = getContext().getContentResolver().openFileDescriptor(imageUri, "r");
            FileDescriptor fd = pfd.getFileDescriptor();
            Bitmap bm = BitmapFactory.decodeFileDescriptor(fd);
            pfd.close();

            if (bm == null) {

            }

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bm_angle = 90.0f;
                    bm_width = bm.getWidth();
                    bm_height = bm.getHeight();
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bm_angle = 180.0f;
                    bm_width = bm.getWidth();
                    bm_height = bm.getHeight();
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bm_angle = 180.0f;
                    bm_width = bm.getHeight();
                    bm_height = bm.getWidth();
                    break;
                default:
                    bm_angle = 0.0f;
                    bm_width = bm.getWidth();
                    bm_height = bm.getHeight();
                    break;
            }

            Matrix matrix = new Matrix();
            matrix.setRotate(bm_angle);
            bm = Bitmap.createBitmap(bm, 0, 0, bm_width, bm_height, matrix,true);
            this.setBitmap(bm);

        } catch (Exception e) {
            Log.e(null, "unable to create bitmap");
        }
//                    catch (ImageProcessingException e) {
//                        Log.e(null, "unable to process information");
//                    }
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
        _imageView.setPosition(
                this.getWidth() * 0.5f,
                this.getHeight() * 0.5f
        );

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        super.onTouchEvent(event);
        _scaleGestureDetector.onTouchEvent(event);
        _gestureDetector.onTouchEvent(event);
//        if (_scaleGestureDetector.onTouchEvent(event) || _gestureDetector.onTouchEvent(event)) {
//            return true;
//        }


        float x = event.getX(), y = event.getY();
        float x2 = 0.0f, y2 = 0.0f;
        int width_container = this.getWidth(), height_container = this.getHeight();

        // * index and pointer index of the current event;
        int pointerIDX = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIDX);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("otv", "ACTION_DOWN");

                pointer1IDX = pointerIDX;
                pointer1ID = pointerID;


                PointF imageViewPos = this.getImageViewPosition();
                _touch_delta = new SizeF(
                        event.getX() - imageViewPos.x,
                        event.getY() - imageViewPos.y
                );
                return true;

            case MotionEvent.ACTION_POINTER_DOWN:
                pointer2IDX = pointerIDX;
                pointer2IDX = pointerID;

                x2 = event.getX(pointer2IDX);
                y2 = event.getY(pointer2IDX);
                Log.d("otv", String.format("ACTION_POINTER_DOWN p1:(%f, %f), p2:(%f, %f)", x, y, x2, y2));

                _angle_initial = Math.atan2(y2 - y, x2 - x);

                _angle_initial_imageView = Math.toRadians(_imageView.getRotation());
                Log.d("ang initial", "" + Math.toDegrees(_angle_initial));


                return true;

//                break;

            case MotionEvent.ACTION_MOVE:
//                Log.d("otv", "ACTION_MOVE");
                if (_imageView != null && pointer2IDX != -1) {
                        x2 = event.getX(pointer2IDX);
                        y2 = event.getY(pointer2IDX);
                        double angle = Math.atan2(y2 - y,(x2 - x));
                        Log.d("rota", String.format("imageRot:%f, angle: %f", Math.toDegrees(_imageView.getRotation()), Math.toDegrees(angle)));
                        _imageView.setRotation((float)Math.toDegrees(_angle_initial_imageView + (angle - _angle_initial)));
                        return true;
                } else {

                }

                if (pointerID == pointer1ID) {
                    _imageView.setPosition(x - _touch_delta.getWidth(), y - _touch_delta.getHeight());
                }


                break;

            case MotionEvent.ACTION_UP:
                pointer1ID = pointer1IDX = -1;

            case MotionEvent.ACTION_POINTER_UP:
                pointer2ID = pointer2IDX = -1;
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.d("otv", "ACTION_UP/CANCEL");
                pointer1ID = pointer1IDX = -1;
                pointer2ID = pointer2IDX = -1;
                break;

            default:
                break;
        }

        return true;
    }

    private int    pointer1ID = -1, pointer1IDX = -1, pointer2ID = -1, pointer2IDX = -1;


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
//                bundle.putParcelable(KEY_BITMAP, bitmap);
                PointF imageViewPos = this.getImageViewPosition();
                bundle.putFloat(KEY_POSITION_X, imageViewPos.x);
                bundle.putFloat(KEY_POSITION_Y, imageViewPos.y);
                bundle.putInt(KEY_WIDTH, _imageView.getWidth());
                bundle.putInt(KEY_HEIGHT, _imageView.getHeight());
                bundle.putFloat(KEY_ANGLE, _imageView.getRotation());

                if (_bitmapCache.get(KEY_BITMAP) == null) {
                    _bitmapCache.put(KEY_BITMAP, bitmap);
                }

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
//            Bitmap bm = bundle.getParcelable(KEY_BITMAP);
            Bitmap bm = _bitmapCache.get(KEY_BITMAP);

            if (bm == null) {

            }
            this.setBitmap(bm);

            final Float x = bundle.getFloat(KEY_POSITION_X);
            final Float y = bundle.getFloat(KEY_POSITION_Y);
            final int width = bundle.getInt(KEY_WIDTH);
            final int height = bundle.getInt(KEY_HEIGHT);
            final float angle = bundle.getFloat(KEY_ANGLE);

            _imageView.getLayoutParams().width = width;
            _imageView.getLayoutParams().height = height;

            /// set the _imageView's position after it has been laid out
            _imageView.post(new Runnable() {
                @Override
                public void run() {
                    _imageView.setPosition(x, y);
                    _imageView.setRotation(angle);
                }
            });
        }

        super.onRestoreInstanceState(state);
    }




    /** ImageView used to display the selected image */
    private com.vjc.imagecompare.View.ImageView _imageView;

    /** for detecting double taps */
    private GestureDetector         _gestureDetector;

    /** for detecting pinch gesture */
    private ScaleGestureDetector    _scaleGestureDetector;

    /** difference between where the touch point began, and the _imageView's center */
    private SizeF       _touch_delta = new SizeF(0.0f, 0.0f);

    /** the angle when the rotation gesture begins */
    private double      _angle_initial = 0.0f;
    private double      _angle_initial_imageView = 0.0f;

    /** Keys for restoring state */
    private final static String     KEY_SUPER_PARCELABLE = "super_parcelable";
    private final static String     KEY_POSITION_X = "pos_x", KEY_POSITION_Y = "pos_y";
    private final static String     KEY_WIDTH = "width", KEY_HEIGHT = "height";
    private final static String     KEY_ANGLE = "angle"; // angle of the _imageView
    private final static String     KEY_BITMAP = "bitmap";

    /** to cache bitmap on screen orientaion change (onSaveInstanceState, onRestoreInstanceState) */
    private static LruCache<String, Bitmap>        _bitmapCache;

    private static void initCaches() {
        // setup cache for bitmap
        int availableCache = (int)Runtime.getRuntime().maxMemory() / 1024;
        _bitmapCache = new LruCache<String, Bitmap>(availableCache) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    static {
        initCaches();
    }


    private void init() {
        if (_imageView == null) {
            _imageView = new com.vjc.imagecompare.View.ImageView(getContext());
            _imageView.setBackgroundColor(Color.TRANSPARENT);
            this.addView(_imageView);
        }

        if (_gestureDetector == null) {
            _gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.d(null, "double tap");
                    toggleImageZoom();
                    _imageView.setRotation(0.0f);
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


    }

    /** returns the _imageView position */
    private PointF getImageViewPosition() {
        return new PointF(_imageView.getX() + _imageView.getWidth() * 0.5f, _imageView.getY() + _imageView.getHeight() * 0.5f);
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
