package com.vjc.imagecompare.view

import android.app.Activity
import android.os.Bundle
import com.vjc.imagecompare.R

class CompareActivity constructor() : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_compare);
    }
}