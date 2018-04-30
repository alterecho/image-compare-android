package com.example.vijay.myapplication.Model;

import android.media.ExifInterface;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MetaData {

    /** a list of available tags, with their display names (tag is key, and display name is the value) */
    static Map<String, String> tags;
    static
    {
        try {
            tags = new HashMap<String, String>();
            tags.put(ExifInterface.TAG_APERTURE_VALUE, "Aperture");
            tags.put(ExifInterface.TAG_GPS_LATITUDE, "Latitude");
            tags.put(ExifInterface.TAG_GPS_LATITUDE_REF, "Latitude ref");
            tags.put(ExifInterface.TAG_GPS_LONGITUDE, "Longitude");
            tags.put(ExifInterface.TAG_GPS_LONGITUDE_REF, "Longitude ref");
        } catch (NullPointerException e) {
            Log.d("MetaData", e.getMessage());
            e.printStackTrace();
        }

    };

    public static List<MetaData> metaDataArrayFrom(ExifInterface exifInterface) {

        ArrayList<MetaData> array = new ArrayList<MetaData>();
        Iterator iterator = tags.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)iterator.next();
            String value = exifInterface.getAttribute(entry.getKey());
//            MetaData metaData = new MetaData(entry.getValue(), value);
            MetaData metaData = new MetaData(entry.getKey(), entry.getValue());

            array.add(metaData);
        }



        return array;
    }


    public String getName() {
        return _name;
    }

    public String getValue() {
        return _value;
    }

    public MetaData(String name, String value) {
        _name = name;
        _value = value;
    }

    /** The name of the Metadata instance */
    private String      _name;

    /** The value of the Metadata instance */
    private String      _value;
}
