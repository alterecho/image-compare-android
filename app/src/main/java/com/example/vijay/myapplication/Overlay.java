package com.example.vijay.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
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
            if (viewParent instanceof ViewGroup) {
                ((ViewGroup) viewParent).removeView(this);
            }
        }

        this.getLayoutParams().width = viewGroup.getWidth();
        this.getLayoutParams().height = viewGroup.getHeight();
        viewGroup.addView(this);
    }




    private void init() {
        this.setBackgroundColor(Color.argb(127, 0, 0, 0));
    }


}
