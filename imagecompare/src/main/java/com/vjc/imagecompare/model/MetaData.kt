package com.vjc.imagecompare.model

data class MetaData constructor(val name: String, val value: String) {
    companion object {
        fun metaDataArray(): Array<MetaData> {
            val array = arrayListOf<MetaData>()
            for (i in 0..10) {
                array.add(MetaData("meta $1", "value $i"))
            }
            return array.toArray(arrayOf())
        }
    }
}