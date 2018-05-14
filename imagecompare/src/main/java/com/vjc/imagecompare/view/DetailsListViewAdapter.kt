package com.vjc.imagecompare.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.vjc.imagecompare.R
import com.vjc.imagecompare.model.MetaData

class DetailsListViewAdapter constructor(ctx: Context) : ArrayAdapter<MetaData>(ctx, 0) {

    var tableData: Array<MetaData>
    get() = _tableData.toArray(arrayOf())
    set(value) {
        _tableData.clear()
        _tableData.addAll(value)
        this.notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return _tableData.count()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = DetailsListViewRow(this.context)
        }

        if (view is DetailsListViewRow) {
            view.metaData = _tableData[position]
        }

        return view
    }

    private val _tableData: ArrayList<MetaData> = ArrayList<MetaData>()

}