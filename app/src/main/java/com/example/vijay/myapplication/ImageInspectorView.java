package com.example.vijay.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class ImageInspectorView extends RelativeLayout {

    public ImageInspectorView(Context context) {
        super(context);
    }

    public ImageInspectorView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
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
}
