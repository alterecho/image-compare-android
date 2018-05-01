package com.example.vijay.myapplication.Model;

import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MetaData {

    public static class MetaDataException extends Exception {
        public MetaDataException(String message) {
            super(message);
        }
    }

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

    }

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

    public static List<MetaData> metaDataArrayFrom(Uri imageURI, Context ctx) throws MetaDataException {
        try {
            InputStream iStream = ctx.getContentResolver().openInputStream(imageURI);
            File file = new File(imageURI.getPath());
            ExifInterface exifInterface = new ExifInterface(iStream);
            List<MetaData> list = MetaData.metaDataArrayFrom(exifInterface);
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new MetaDataException("Ünable to retreive metadata for imageURI:" + imageURI);
        }
//                        BufferedInputStream bis = new BufferedInputStream(iStream);
//                        Metadata metadata = ImageMetadataReader.readMetadata(bis,iStream.available());
//
//                        Metadata md = ImageMetadataReader.readMetadata(file);
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
