package com.vjc.imagecompare.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.vjc.imagecompare.R
import com.vjc.imagecompare.extensions.center
import com.vjc.imagecompare.extensions.removeFromParent
import com.vjc.imagecompare.model.MetaData

class ImageDetailsView : LinearLayout {

    var metaData: List<MetaData>?
        get() = _adapter.tableData
        set(value) {
            _adapter.tableData = value
        }

    init {
        val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_details, this, true)
//        View.inflate(this.context, R.layout.view_details, this)

        _listView = view.findViewById(R.id.listView)
        _adapter = DetailsListViewAdapter(this.context)
        _listView.adapter = _adapter

    }

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

//    val isShown: Boolean
//    get() = this.parent != null

    public fun showIn(viewGroup: ViewGroup) {

        // remove the views
        if (overlay == null) {
            _overlay?.removeFromParent()
        } else {
            this.initOverlay()
        }
        viewGroup.removeView(this)

        // add the views
        viewGroup.addView(_overlay!!)
        viewGroup.addView(this)

        val layoutParam = this.layoutParams
        layoutParam.width = (viewGroup.width * 0.75f).toInt()
        layoutParam.height = (viewGroup.height * 0.75f).toInt()
        viewGroup.requestLayout()

        this.post( {
            //TODO: thread safety
            this.center()
        })
    }

    fun close(): Boolean {

        val parent = this.parent

        if (parent !is ViewGroup) {
            return false
        }

        parent.removeView(this)
        _overlay?.let {
            parent.removeView(it)
        }

        return true
    }




    private val _listView: ListView
    private val _adapter: DetailsListViewAdapter

    /** a semi-transparent overlay view that will be placed on parent before placing 'this' on parentView */
    private var _overlay: View? = null

    private fun initOverlay() {
        _overlay = View(this.context)
        _overlay?.setBackgroundColor(Color.argb(125, 0, 0, 0))
        _overlay?.setOnClickListener( {
            close()
        })

    }

}