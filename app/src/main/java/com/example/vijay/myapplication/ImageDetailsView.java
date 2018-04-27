package com.example.vijay.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class ImageDetailsView extends View {

    ImageDetailsView(Context ctx) {
        super(ctx);
        init();
    }

    ImageDetailsView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        init();
    }


    private void init() {
        this.setBackgroundColor(Color.BLUE);
    }
}
