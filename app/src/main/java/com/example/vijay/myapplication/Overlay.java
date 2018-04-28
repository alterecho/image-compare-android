package com.example.vijay.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class Overlay extends View {
    Overlay(Context ctx) {
        super(ctx);
        init();
    }

    Overlay(Context ctx, AttributeSet attrbs) {
        super(ctx, attrbs);
        init();
    }

    public void show(View view, ViewGroup viewGroup) {
        ViewParent viewParent = this.getParent();

        if (viewParent != null) {
            this.hide();
            return;
        }

        viewGroup.addView(this);
        this.getLayoutParams().width = viewGroup.getWidth();
        this.getLayoutParams().height = viewGroup.getHeight();

    }

    public void hide() {
        if (this.getParent() != null && this.getParent() instanceof ViewGroup) {
            ((ViewGroup) this.getParent()).removeView(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                _touch_down = true;
                return true;
//                break;

            case MotionEvent.ACTION_UP:
                if (_touch_down) {
                    this.hide();
                }

                _touch_down = false;
                break;

                default:
                    break;
        }

        return super.onTouchEvent(event);
    }




    /** indicates if touch down event happened on this view */
    private boolean         _touch_down = false;

    private void init() {
        this.setBackgroundColor(Color.argb(127, 0, 0, 0));
    }


}
