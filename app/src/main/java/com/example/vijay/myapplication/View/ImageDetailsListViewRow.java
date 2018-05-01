package com.example.vijay.myapplication.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vijay.myapplication.Model.MetaData;
import com.example.vijay.myapplication.R;

public class ImageDetailsListViewRow extends LinearLayout {
    public ImageDetailsListViewRow(Context ctx) {
        super(ctx);
        init();
    }

    public void set(MetaData metaData) {
        _metaData = metaData;

        String name = null, value = null;

        if (_metaData != null) {
            name = metaData.getName();
            value = metaData.getValue();
        }

        _nameTextView.setText(name);
        _valueTextView.setText(value);
    }






    /** The current MetaData this row represents/displays */
    private MetaData        _metaData = null;

    private TextView        _nameTextView = null;
    private TextView        _valueTextView = null;

    private void init() {
        Context ctx = getContext();

        //* inflate and attach the layout for this view
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_image_details_list_view, this, true);

        //* retrieve the views
        _nameTextView = (TextView)view.findViewById(R.id.name);
        _valueTextView = (TextView)view.findViewById(R.id.value);
    }
}
