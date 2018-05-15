package com.vjc.imagecompare.extensions

import android.graphics.PointF

fun PointF.byAdding(point: PointF): PointF = PointF(this.x + point.x, this.y + point.y)
fun PointF.bySubtracting(point: PointF): PointF = PointF(this.x - point.x, this.y - point.y)