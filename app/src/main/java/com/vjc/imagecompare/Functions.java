package com.vjc.imagecompare;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SizeF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class Functions {

    /** Centers the view in its parentView */
    static void centerView(@NonNull View view, @NonNull View parentView) {
        SizeF size = new SizeF(view.getWidth(), view.getHeight());
        SizeF size_parent = new SizeF(parentView.getWidth(), parentView.getHeight());

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

    /**
     * Convenience method to remove a View from it's parent
     * @param view the View to remove from its parent
     */
    static void removeFromParent(View view) {
        ViewParent viewParent = view.getParent();
        if (viewParent == null) {
            return;
        }

        if (viewParent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)viewParent;
            viewGroup.removeView(view);
        }
    }

    static String stringFrom(Bundle bundle) {
        String ret = "bundle:\n";

        for (String key : bundle.keySet()) {
            ret += key + ": " + bundle.get(key).toString();
        }

        return ret;
    }

    static void print(Bundle bundle) {
        Log.d("bundle", stringFrom(bundle));
    }
}
