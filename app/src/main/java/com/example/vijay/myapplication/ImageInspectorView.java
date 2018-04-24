package com.example.vijay.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class ImageInspectorView extends RelativeLayout {

    public ImageInspectorView(Context context) {
        super(context);
        init();
    }

    public ImageInspectorView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        init();
    }



    public void setBitmap(Bitmap bm) {
//        _imageView.setImageBitmap(bm);

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



    private void init() {
        if (_imageView == null) {
            _imageView = new ImageView(getContext());
            _imageView.setBackgroundColor(Color.RED);
            this.addView(_imageView);
        }

        Random rnd = new Random();
        setBackgroundColor(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
    }

    /** ImageView used to display the selected image */
    private ImageView       _imageView;


}
