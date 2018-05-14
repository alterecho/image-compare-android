package com.vjc.imagecompare.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.vjc.imagecompare.R

class DetailsListViewAdapter constructor(ctx: Context) : ArrayAdapter<String>(ctx, 0) {

    override fun getCount(): Int {
        return 10
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.row_details, parent, false)
        }

        return view!!
    }
}