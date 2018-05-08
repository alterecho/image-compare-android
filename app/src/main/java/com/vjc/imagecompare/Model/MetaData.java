package com.vjc.imagecompare.Model;

import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
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
            tags.put(ExifInterface.TAG_ARTIST, "Artist");
            tags.put(ExifInterface.TAG_BITS_PER_SAMPLE, "BitsPerSample");
            tags.put(ExifInterface.TAG_COMPRESSION, "Compression");
            tags.put(ExifInterface.TAG_COPYRIGHT, "Copyright");
            tags.put(ExifInterface.TAG_DATETIME, "DateTime");
            tags.put(ExifInterface.TAG_IMAGE_DESCRIPTION, "ImageDescription");
            tags.put(ExifInterface.TAG_IMAGE_LENGTH, "ImageLength");
            tags.put(ExifInterface.TAG_IMAGE_WIDTH, "ImageWidth");
            tags.put(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT, "JPEGInterchangeFormat");
            tags.put(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, "JPEGInterchangeFormatLength");
            tags.put(ExifInterface.TAG_MAKE, "Make");
            tags.put(ExifInterface.TAG_MODEL, "Model");
            tags.put(ExifInterface.TAG_ORIENTATION, "Orientation");
            tags.put(ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION, "PhotometricInterpretation");
            tags.put(ExifInterface.TAG_PLANAR_CONFIGURATION, "PlanarConfiguration");
            tags.put(ExifInterface.TAG_PRIMARY_CHROMATICITIES, "PrimaryChromaticities");
            tags.put(ExifInterface.TAG_REFERENCE_BLACK_WHITE, "ReferenceBlackWhite");
            tags.put(ExifInterface.TAG_RESOLUTION_UNIT, "ResolutionUnit");
            tags.put(ExifInterface.TAG_ROWS_PER_STRIP, "RowsPerStrip");
            tags.put(ExifInterface.TAG_SAMPLES_PER_PIXEL, "SamplesPerPixel");
            tags.put(ExifInterface.TAG_SOFTWARE, "Software");
            tags.put(ExifInterface.TAG_STRIP_BYTE_COUNTS, "StripByteCounts");
            tags.put(ExifInterface.TAG_STRIP_OFFSETS, "StripOffsets");
            tags.put(ExifInterface.TAG_TRANSFER_FUNCTION, "TransferFunction");
            tags.put(ExifInterface.TAG_WHITE_POINT, "WhitePoint");
            tags.put(ExifInterface.TAG_X_RESOLUTION, "XResolution");
            tags.put(ExifInterface.TAG_Y_CB_CR_COEFFICIENTS, "YCbCrCoefficients");
            tags.put(ExifInterface.TAG_Y_CB_CR_POSITIONING, "YCbCrPositioning");
            tags.put(ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING, "YCbCrSubSampling");
            tags.put(ExifInterface.TAG_Y_RESOLUTION, "YResolution");
            tags.put(ExifInterface.TAG_APERTURE_VALUE, "ApertureValue");
            tags.put(ExifInterface.TAG_BRIGHTNESS_VALUE, "BrightnessValue");
            tags.put(ExifInterface.TAG_CFA_PATTERN, "CFAPattern");
            tags.put(ExifInterface.TAG_COLOR_SPACE, "ColorSpace");
            tags.put(ExifInterface.TAG_COMPONENTS_CONFIGURATION, "ComponentsConfiguration");
            tags.put(ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL, "CompressedBitsPerPixel");
            tags.put(ExifInterface.TAG_CONTRAST, "Contrast");
            tags.put(ExifInterface.TAG_CUSTOM_RENDERED, "CustomRendered");
            tags.put(ExifInterface.TAG_DATETIME_DIGITIZED, "DateTimeDigitized");
            tags.put(ExifInterface.TAG_DATETIME_ORIGINAL, "DateTimeOriginal");
            tags.put(ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION, "DeviceSettingDescription");
            tags.put(ExifInterface.TAG_DIGITAL_ZOOM_RATIO, "DigitalZoomRatio");
            tags.put(ExifInterface.TAG_EXIF_VERSION, "ExifVersion");
            tags.put(ExifInterface.TAG_EXPOSURE_BIAS_VALUE, "ExposureBiasValue");
            tags.put(ExifInterface.TAG_EXPOSURE_INDEX, "ExposureIndex");
            tags.put(ExifInterface.TAG_EXPOSURE_MODE, "ExposureMode");
            tags.put(ExifInterface.TAG_EXPOSURE_PROGRAM, "ExposureProgram");
            tags.put(ExifInterface.TAG_EXPOSURE_TIME, "ExposureTime");
            tags.put(ExifInterface.TAG_F_NUMBER, "FNumber");
            tags.put(ExifInterface.TAG_APERTURE, "FNumber");
            tags.put(ExifInterface.TAG_FILE_SOURCE, "FileSource");
            tags.put(ExifInterface.TAG_FLASH, "Flash");
            tags.put(ExifInterface.TAG_FLASH_ENERGY, "FlashEnergy");
            tags.put(ExifInterface.TAG_FLASHPIX_VERSION, "FlashpixVersion");
            tags.put(ExifInterface.TAG_FOCAL_LENGTH, "FocalLength");
            tags.put(ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM, "FocalLengthIn35mmFilm");
            tags.put(ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, "FocalPlaneResolutionUnit");
            tags.put(ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION, "FocalPlaneXResolution");
            tags.put(ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION, "FocalPlaneYResolution");
            tags.put(ExifInterface.TAG_GAIN_CONTROL, "GainControl");
            tags.put(ExifInterface.TAG_ISO_SPEED_RATINGS, "ISOSpeedRatings");
            tags.put(ExifInterface.TAG_ISO, "ISOSpeedRatings");
            tags.put(ExifInterface.TAG_IMAGE_UNIQUE_ID, "ImageUniqueID");
            tags.put(ExifInterface.TAG_LIGHT_SOURCE, "LightSource");
            tags.put(ExifInterface.TAG_MAKER_NOTE, "MakerNote");
            tags.put(ExifInterface.TAG_MAX_APERTURE_VALUE, "MaxApertureValue");
            tags.put(ExifInterface.TAG_METERING_MODE, "MeteringMode");
            tags.put(ExifInterface.TAG_NEW_SUBFILE_TYPE, "NewSubfileType");
            tags.put(ExifInterface.TAG_OECF, "OECF");
            tags.put(ExifInterface.TAG_PIXEL_X_DIMENSION, "PixelXDimension");
            tags.put(ExifInterface.TAG_PIXEL_Y_DIMENSION, "PixelYDimension");
            tags.put(ExifInterface.TAG_RELATED_SOUND_FILE, "RelatedSoundFile");
            tags.put(ExifInterface.TAG_SATURATION, "Saturation");
            tags.put(ExifInterface.TAG_SCENE_CAPTURE_TYPE, "SceneCaptureType");
            tags.put(ExifInterface.TAG_SCENE_TYPE, "SceneType");
            tags.put(ExifInterface.TAG_SENSING_METHOD, "SensingMethod");
            tags.put(ExifInterface.TAG_SHARPNESS, "Sharpness");
            tags.put(ExifInterface.TAG_SHUTTER_SPEED_VALUE, "ShutterSpeedValue");
            tags.put(ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE, "SpatialFrequencyResponse");
            tags.put(ExifInterface.TAG_SPECTRAL_SENSITIVITY, "SpectralSensitivity");
            tags.put(ExifInterface.TAG_SUBFILE_TYPE, "SubfileType");
            tags.put(ExifInterface.TAG_SUBSEC_TIME, "SubSecTime");
            tags.put(ExifInterface.TAG_SUBSEC_TIME_DIG, "SubSecTimeDigitized");
            tags.put(ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, "SubSecTimeDigitized");
            tags.put(ExifInterface.TAG_SUBSEC_TIME_ORIG, "SubSecTimeOriginal");
            tags.put(ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, "SubSecTimeOriginal");
            tags.put(ExifInterface.TAG_SUBJECT_AREA, "SubjectArea");
            tags.put(ExifInterface.TAG_SUBJECT_DISTANCE, "SubjectDistance");
            tags.put(ExifInterface.TAG_SUBJECT_DISTANCE_RANGE, "SubjectDistanceRange");
            tags.put(ExifInterface.TAG_SUBJECT_LOCATION, "SubjectLocation");
            tags.put(ExifInterface.TAG_USER_COMMENT, "UserComment");
            tags.put(ExifInterface.TAG_WHITE_BALANCE, "WhiteBalance");
            tags.put(ExifInterface.TAG_GPS_ALTITUDE, "GPSAltitude");
            tags.put(ExifInterface.TAG_GPS_ALTITUDE_REF, "GPSAltitudeRef");
            tags.put(ExifInterface.TAG_GPS_AREA_INFORMATION, "GPSAreaInformation");
            tags.put(ExifInterface.TAG_GPS_DOP, "GPSDOP");
            tags.put(ExifInterface.TAG_GPS_DATESTAMP, "GPSDateStamp");
            tags.put(ExifInterface.TAG_GPS_DEST_BEARING, "GPSDestBearing");
            tags.put(ExifInterface.TAG_GPS_DEST_BEARING_REF, "GPSDestBearingRef");
            tags.put(ExifInterface.TAG_GPS_DEST_DISTANCE, "GPSDestDistance");
            tags.put(ExifInterface.TAG_GPS_DEST_DISTANCE_REF, "GPSDestDistanceRef");
            tags.put(ExifInterface.TAG_GPS_DEST_LATITUDE, "GPSDestLatitude");
            tags.put(ExifInterface.TAG_GPS_DEST_LATITUDE_REF, "GPSDestLatitudeRef");
            tags.put(ExifInterface.TAG_GPS_DEST_LONGITUDE, "GPSDestLongitude");
            tags.put(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF, "GPSDestLongitudeRef");
            tags.put(ExifInterface.TAG_GPS_DIFFERENTIAL, "GPSDifferential");
            tags.put(ExifInterface.TAG_GPS_IMG_DIRECTION, "GPSImgDirection");
            tags.put(ExifInterface.TAG_GPS_IMG_DIRECTION_REF, "GPSImgDirectionRef");
            tags.put(ExifInterface.TAG_GPS_LATITUDE, "GPSLatitude");
            tags.put(ExifInterface.TAG_GPS_LATITUDE_REF, "GPSLatitudeRef");
            tags.put(ExifInterface.TAG_GPS_LONGITUDE, "GPSLongitude");
            tags.put(ExifInterface.TAG_GPS_LONGITUDE_REF, "GPSLongitudeRef");
            tags.put(ExifInterface.TAG_GPS_MAP_DATUM, "GPSMapDatum");
            tags.put(ExifInterface.TAG_GPS_MEASURE_MODE, "GPSMeasureMode");
            tags.put(ExifInterface.TAG_GPS_PROCESSING_METHOD, "GPSProcessingMethod");
            tags.put(ExifInterface.TAG_GPS_SATELLITES, "GPSSatellites");
            tags.put(ExifInterface.TAG_GPS_SPEED, "GPSSpeed");
            tags.put(ExifInterface.TAG_GPS_SPEED_REF, "GPSSpeedRef");
            tags.put(ExifInterface.TAG_GPS_STATUS, "GPSStatus");
            tags.put(ExifInterface.TAG_GPS_TIMESTAMP, "GPSTimeStamp");
            tags.put(ExifInterface.TAG_GPS_TRACK, "GPSTrack");
            tags.put(ExifInterface.TAG_GPS_TRACK_REF, "GPSTrackRef");
            tags.put(ExifInterface.TAG_GPS_VERSION_ID, "GPSVersionID");
            tags.put(ExifInterface.TAG_INTEROPERABILITY_INDEX, "InteroperabilityIndex");
            tags.put(ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH, "ThumbnailImageLength");
            tags.put(ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH, "ThumbnailImageWidth");
            tags.put(ExifInterface.TAG_DNG_VERSION, "DNGVersion");
            tags.put(ExifInterface.TAG_DEFAULT_CROP_SIZE, "DefaultCropSize");
            tags.put(ExifInterface.TAG_ORF_THUMBNAIL_IMAGE, "ThumbnailImage");
            tags.put(ExifInterface.TAG_ORF_PREVIEW_IMAGE_START, "PreviewImageStart");
            tags.put(ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH, "PreviewImageLength");
            tags.put(ExifInterface.TAG_ORF_ASPECT_FRAME, "AspectFrame");
            tags.put(ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER, "SensorBottomBorder");
            tags.put(ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER, "SensorLeftBorder");
            tags.put(ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER, "SensorRightBorder");
            tags.put(ExifInterface.TAG_RW2_SENSOR_TOP_BORDER, "SensorTopBorder");
            tags.put(ExifInterface.TAG_RW2_ISO, "ISO");
            tags.put(ExifInterface.TAG_RW2_JPG_FROM_RAW, "JpgFromRaw");
        } catch (NullPointerException e) {
            Log.d("MetaData", e.getMessage());
            e.printStackTrace();
        }

    }

    public static List<MetaData> metaDataArrayFrom(ExifInterface exifInterface) {
        ArrayList<MetaData> array = new ArrayList<MetaData>();
        Iterator iterator = MetaData.tags.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)iterator.next();
            String value = exifInterface.getAttribute(entry.getKey());
            if (value == null) {
                value = "--";
            }
            MetaData metaData = new MetaData(entry.getValue(), value);
//            MetaData metaData = new MetaData(entry.getKey(), entry.getValue());
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
