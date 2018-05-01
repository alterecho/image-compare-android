package com.example.vijay.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vijay.myapplication.Model.MetaData;

import org.w3c.dom.Text;

public class ImageDetailsListViewAdapter extends ArrayAdapter<String> {
    public ImageDetailsListViewAdapter(Context ctx) {
        super(ctx, R.layout.row_image_details_list_view);
    }

    @Override
    public int getCount() {

//        return 5;

        if (_dataSet == null) {
            return 0;
        }
        
        return _dataSet.length;
    }

    public void setDataSet(MetaData[] _dataSet) {
        this._dataSet = _dataSet;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_image_details_list_view, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.name);
        TextView valueTextView = (TextView)convertView.findViewById(R.id.value);

        nameTextView.setText("titl");
        valueTextView.setText("val1");

        return convertView;
    }




    /** data set used by this adapter */
    private MetaData[]      _dataSet = null;
}
