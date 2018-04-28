package com.example.vijay.myapplication;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.SizeF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class Functions {

    /** Centers the view in its parentView */
    static void centerView(@NonNull View view, @NonNull View parentView) {
        SizeF size = new SizeF(view.getWidth(), view.getHeight());
        SizeF size_parent = new SizeF(view.getWidth(), view.getHeight());

        PointF center_parent = new PointF(size_parent.getWidth() * 0.5f, size_parent.getHeight() * 0.5f);

        view.setX(center_parent.x - size.getWidth() * 0.5f);
        view.setY(center_parent.y - size.getHeight() * 0.5f);
    }

    /** Centers view in view's parent */
    static void centerView(@NonNull View view) {
        ViewParent viewParent = view.getParent();
        if (!(viewParent instanceof ViewGroup)) {
            return;
        }

        ViewGroup layout = (ViewGroup) viewParent;
        centerView(view, layout);
    }
}
