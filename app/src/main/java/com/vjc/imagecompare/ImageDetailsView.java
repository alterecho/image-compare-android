package com.vjc.imagecompare;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.vjc.imagecompare.Model.MetaData;

public class ImageDetailsView extends RelativeLayout {

    ImageDetailsView(Context ctx) {
        super(ctx);
        init();
    }

    ImageDetailsView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        init();
    }

    public void setData(MetaData[] data) {
        _adapter.setDataSet(data);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        _adapter.notifyDataSetChanged();
    }

    /** used to display the list of available MetaData */
    private ListView        _listView;

    /** the adapter set for the _listView */
    private ImageDetailsListViewAdapter     _adapter;

    private void init() {
//        this.setBackgroundColor(Color.BLUE);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_image_details, this, true);
        //or
        //inflate(getContext(), R.layout.view_image_details, this);

        _listView = (ListView)view.findViewById(R.id.listView);
        _listView.post(new Runnable() {
            @Override
            public void run() {
                ((ImageDetailsListViewAdapter)_listView.getAdapter()).notifyDataSetChanged();
            }
        });

        _adapter = new ImageDetailsListViewAdapter(getContext());
        _listView.setAdapter(_adapter);
    }

}
