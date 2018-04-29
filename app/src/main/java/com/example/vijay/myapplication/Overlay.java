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

    public void show(final View view, ViewGroup viewGroup) {
        if (_targetView != null) {
            Functions.removeFromParent(_targetView);
            _targetView = null;
        }

        ViewParent viewParent = this.getParent();

        if (viewParent != null) {
            this.hide();
            return;
        }

        viewGroup.addView(this);
        this.getLayoutParams().width = viewGroup.getWidth();
        this.getLayoutParams().height = viewGroup.getHeight();

        _targetView = view;
        viewGroup.addView(view);
        view.getLayoutParams().width = (int)(viewGroup.getWidth() * 0.75f);
        view.getLayoutParams().height = (int)(viewGroup.getHeight() * 0.75f);

        view.post(new Runnable() {
            @Override
            public void run() {
                Functions.centerView(view);
            }
        });

    }

    public void hide() {
        if (_targetView != null) {
            Functions.removeFromParent(_targetView);
        }

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

    /** the view to show the overlay for */
    private View            _targetView = null;

    private void init() {
        this.setBackgroundColor(Color.argb(127, 0, 0, 0));
    }


}
