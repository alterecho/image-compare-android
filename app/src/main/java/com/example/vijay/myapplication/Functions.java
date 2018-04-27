package com.example.vijay.myapplication;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.SizeF;
import android.view.View;

public class Functions {
    static void centerView(@NonNull View view, @NonNull View parentView) {
        SizeF size = new SizeF(view.getWidth(), view.getHeight());
        SizeF size_parent = new SizeF(view.getWidth(), view.getHeight());

        PointF center_parent = new PointF(size_parent.getWidth() * 0.5f, size_parent.getHeight() * 0.5f);

        view.setX(center_parent.x - size.getWidth() * 0.5f);
        view.setY(center_parent.y - size.getHeight() * 0.5f);
    }
}
