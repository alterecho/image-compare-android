package com.vjc.imagecompare.View;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.AppCompatImageView;
import android.view.ViewGroup;

public class ImageView extends AppCompatImageView {
    public ImageView(Context ctx) {
        super(ctx);
    }

    public void setPosition(Float x, Float y) {

        PointF p = this.getCorrectedPosition(x, y);

        int width = this.getWidth();
        int height = this.getHeight();

        this.setX(p.x - width * 0.5f);
        this.setY(p.y - height * 0.5f);
    }

    public void setPosition(PointF point) {
        this.setPosition(point.x, point.y);
    }

    public void setBorder(Float border) {
        _border = border;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    private Float       _border = 10.0f;
    /** returns a point that is restricted point for the _imageView (So as to not move the _imageView out of view completely)*/
    private PointF getCorrectedPosition(Float x, Float y) {
        PointF p = new PointF(x, y);

        if (this.getParent() == null || !(this.getParent() instanceof ViewGroup)) {
            return p;
        }

        ViewGroup viewGroup = (ViewGroup)this.getParent();

        int width = this.getWidth(); int height = this.getHeight();
        int width_parent = viewGroup.getWidth(); int height_parent = viewGroup.getHeight();

        if (p.x + width * 0.5f < _border) {
            p.x = _border - width * 0.5f;
        } else if (p.x - width * 0.5f > width_parent - _border) {
            p.x = width_parent - _border + width * 0.5f;
        }

        if (p.y + height * 0.5f < _border) {
            p.y = _border - height * 0.5f;
        } else if (p.y - height * 0.5f > height_parent - _border) {
            p.y = height_parent - _border + height * 0.5f;
        }

        return p;
    }
}
