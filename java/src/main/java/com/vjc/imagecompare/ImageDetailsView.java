package com.vjc.imagecompare;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.vjc.imagecompare.Model.MetaData;

import java.lang.ref.WeakReference;

public class ImageDetailsView extends LinearLayout {

    interface Listener {
        void closeClicked(ImageDetailsView view);
    }

    ImageDetailsView(Context ctx) {
        super(ctx);
        init();
    }

    ImageDetailsView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        init();
    }

    public void setDelegate(ImageDetailsView.Listener delegate) {
        _delegate = new WeakReference<>(delegate);
    }

    public void setData(MetaData[] data) {
        _adapter.setDataSet(data);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        _adapter.notifyDataSetChanged();
    }



    private WeakReference<ImageDetailsView.Listener>    _delegate;

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

        ImageButton closeButton = (ImageButton)findViewById(R.id.btn_close);

        final WeakReference<ImageDetailsView> _this = new WeakReference<>(this);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _delegate.get().closeClicked(_this.get());
            }
        });
    }

}
