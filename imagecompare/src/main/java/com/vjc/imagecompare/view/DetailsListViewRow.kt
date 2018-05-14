package com.vjc.imagecompare.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.vjc.imagecompare.R
import com.vjc.imagecompare.model.MetaData

class DetailsListViewRow constructor(ctx: Context) : LinearLayout(ctx) {


    var metaData: MetaData? = null
    set(value) {
        field = value
        _nameTextView.text = field?.name
        _valueTextView.text = field?.value
    }


    // compile error if placed below init
    private val _nameTextView: TextView
    private val _valueTextView: TextView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.row_details, this, true)
        _nameTextView = view.findViewById(R.id.nameTextView)
        _valueTextView = view.findViewById(R.id.valueTextView)
    }




}