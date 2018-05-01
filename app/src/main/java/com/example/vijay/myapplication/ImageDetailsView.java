package com.example.vijay.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ImageDetailsView extends RelativeLayout {

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
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_image_details, this, true);
        //or
        //inflate(getContext(), R.layout.view_image_details, this);

        _listView = (ListView)view.findViewById(R.id.listView);
        _listView.setAdapter(new ImageDetailsListViewAdapter(getContext()));
    }



    private ListView _listView;
}
