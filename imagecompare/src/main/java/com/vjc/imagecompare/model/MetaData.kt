package com.vjc.imagecompare.model

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import java.io.InputStream

data class MetaData constructor(val name: String, val value: String) {
    companion object {
        fun metaDataArray(uri: Uri, ctx: Context): Array<MetaData> {
            val array = arrayListOf<MetaData>()

            val iStream = ctx.contentResolver.openInputStream(uri)
            val exifInterface = ExifInterface(iStream)

            for ((key, value) in tags) {
                // value for the given key (from ExifInterface object)
                val attribValue = exifInterface.getAttribute(key)
                if (attribValue != null) {
                    array.add(MetaData(key, attribValue))
                } else {
                    array.add(MetaData(key, "-"))
                }
            }
            return array.toArray(arrayOf())
        }

        /** map of all available MetaData */
        val tags: Map<String, String>
        init {
            val map = HashMap<String, String>()
            map.put(ExifInterface.TAG_APERTURE_VALUE, "Aperture")
            map.put(ExifInterface.TAG_GPS_LATITUDE, "Latitude")
            map.put(ExifInterface.TAG_GPS_LATITUDE_REF, "Latitude ref")
            map.put(ExifInterface.TAG_GPS_LONGITUDE, "Longitude")
            map.put(ExifInterface.TAG_GPS_LONGITUDE_REF, "Longitude ref")
            map.put(ExifInterface.TAG_ARTIST, "Artist")
            map.put(ExifInterface.TAG_BITS_PER_SAMPLE, "BitsPerSample")
            map.put(ExifInterface.TAG_COMPRESSION, "Compression")
            map.put(ExifInterface.TAG_COPYRIGHT, "Copyright")
            map.put(ExifInterface.TAG_DATETIME, "DateTime")
            map.put(ExifInterface.TAG_IMAGE_DESCRIPTION, "ImageDescription")
            map.put(ExifInterface.TAG_IMAGE_LENGTH, "ImageLength")
            map.put(ExifInterface.TAG_IMAGE_WIDTH, "ImageWidth")
            map.put(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT, "JPEGInterchangeFormat")
            map.put(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, "JPEGInterchangeFormatLength")
            map.put(ExifInterface.TAG_MAKE, "Make")
            map.put(ExifInterface.TAG_MODEL, "Model")
            map.put(ExifInterface.TAG_ORIENTATION, "Orientation")
            map.put(ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION, "PhotometricInterpretation")
            map.put(ExifInterface.TAG_PLANAR_CONFIGURATION, "PlanarConfiguration")
            map.put(ExifInterface.TAG_PRIMARY_CHROMATICITIES, "PrimaryChromaticities")
            map.put(ExifInterface.TAG_REFERENCE_BLACK_WHITE, "ReferenceBlackWhite")
            map.put(ExifInterface.TAG_RESOLUTION_UNIT, "ResolutionUnit")
            map.put(ExifInterface.TAG_ROWS_PER_STRIP, "RowsPerStrip")
            map.put(ExifInterface.TAG_SAMPLES_PER_PIXEL, "SamplesPerPixel")
            map.put(ExifInterface.TAG_SOFTWARE, "Software")
            map.put(ExifInterface.TAG_STRIP_BYTE_COUNTS, "StripByteCounts")
            map.put(ExifInterface.TAG_STRIP_OFFSETS, "StripOffsets")
            map.put(ExifInterface.TAG_TRANSFER_FUNCTION, "TransferFunction")
            map.put(ExifInterface.TAG_WHITE_POINT, "WhitePoint")
            map.put(ExifInterface.TAG_X_RESOLUTION, "XResolution")
            map.put(ExifInterface.TAG_Y_CB_CR_COEFFICIENTS, "YCbCrCoefficients")
            map.put(ExifInterface.TAG_Y_CB_CR_POSITIONING, "YCbCrPositioning")
            map.put(ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING, "YCbCrSubSampling")
            map.put(ExifInterface.TAG_Y_RESOLUTION, "YResolution")
            map.put(ExifInterface.TAG_APERTURE_VALUE, "ApertureValue")
            map.put(ExifInterface.TAG_BRIGHTNESS_VALUE, "BrightnessValue")
            map.put(ExifInterface.TAG_CFA_PATTERN, "CFAPattern")
            map.put(ExifInterface.TAG_COLOR_SPACE, "ColorSpace")
            map.put(ExifInterface.TAG_COMPONENTS_CONFIGURATION, "ComponentsConfiguration")
            map.put(ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL, "CompressedBitsPerPixel")
            map.put(ExifInterface.TAG_CONTRAST, "Contrast")
            map.put(ExifInterface.TAG_CUSTOM_RENDERED, "CustomRendered")
            map.put(ExifInterface.TAG_DATETIME_DIGITIZED, "DateTimeDigitized")
            map.put(ExifInterface.TAG_DATETIME_ORIGINAL, "DateTimeOriginal")
            map.put(ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION, "DeviceSettingDescription")
            map.put(ExifInterface.TAG_DIGITAL_ZOOM_RATIO, "DigitalZoomRatio")
            map.put(ExifInterface.TAG_EXIF_VERSION, "ExifVersion")
            map.put(ExifInterface.TAG_EXPOSURE_BIAS_VALUE, "ExposureBiasValue")
            map.put(ExifInterface.TAG_EXPOSURE_INDEX, "ExposureIndex")
            map.put(ExifInterface.TAG_EXPOSURE_MODE, "ExposureMode")
            map.put(ExifInterface.TAG_EXPOSURE_PROGRAM, "ExposureProgram")
            map.put(ExifInterface.TAG_EXPOSURE_TIME, "ExposureTime")
            map.put(ExifInterface.TAG_F_NUMBER, "FNumber")
            map.put(ExifInterface.TAG_APERTURE, "FNumber")
            map.put(ExifInterface.TAG_FILE_SOURCE, "FileSource")
            map.put(ExifInterface.TAG_FLASH, "Flash")
            map.put(ExifInterface.TAG_FLASH_ENERGY, "FlashEnergy")
            map.put(ExifInterface.TAG_FLASHPIX_VERSION, "FlashpixVersion")
            map.put(ExifInterface.TAG_FOCAL_LENGTH, "FocalLength")
            map.put(ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM, "FocalLengthIn35mmFilm")
            map.put(ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, "FocalPlaneResolutionUnit")
            map.put(ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION, "FocalPlaneXResolution")
            map.put(ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION, "FocalPlaneYResolution")
            map.put(ExifInterface.TAG_GAIN_CONTROL, "GainControl")
            map.put(ExifInterface.TAG_ISO_SPEED_RATINGS, "ISOSpeedRatings")
            map.put(ExifInterface.TAG_ISO, "ISOSpeedRatings")
            map.put(ExifInterface.TAG_IMAGE_UNIQUE_ID, "ImageUniqueID")
            map.put(ExifInterface.TAG_LIGHT_SOURCE, "LightSource")
            map.put(ExifInterface.TAG_MAKER_NOTE, "MakerNote")
            map.put(ExifInterface.TAG_MAX_APERTURE_VALUE, "MaxApertureValue")
            map.put(ExifInterface.TAG_METERING_MODE, "MeteringMode")
            map.put(ExifInterface.TAG_NEW_SUBFILE_TYPE, "NewSubfileType")
            map.put(ExifInterface.TAG_OECF, "OECF")
            map.put(ExifInterface.TAG_PIXEL_X_DIMENSION, "PixelXDimension")
            map.put(ExifInterface.TAG_PIXEL_Y_DIMENSION, "PixelYDimension")
            map.put(ExifInterface.TAG_RELATED_SOUND_FILE, "RelatedSoundFile")
            map.put(ExifInterface.TAG_SATURATION, "Saturation")
            map.put(ExifInterface.TAG_SCENE_CAPTURE_TYPE, "SceneCaptureType")
            map.put(ExifInterface.TAG_SCENE_TYPE, "SceneType")
            map.put(ExifInterface.TAG_SENSING_METHOD, "SensingMethod")
            map.put(ExifInterface.TAG_SHARPNESS, "Sharpness")
            map.put(ExifInterface.TAG_SHUTTER_SPEED_VALUE, "ShutterSpeedValue")
            map.put(ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE, "SpatialFrequencyResponse")
            map.put(ExifInterface.TAG_SPECTRAL_SENSITIVITY, "SpectralSensitivity")
            map.put(ExifInterface.TAG_SUBFILE_TYPE, "SubfileType")
            map.put(ExifInterface.TAG_SUBSEC_TIME, "SubSecTime")
            map.put(ExifInterface.TAG_SUBSEC_TIME_DIG, "SubSecTimeDigitized")
            map.put(ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, "SubSecTimeDigitized")
            map.put(ExifInterface.TAG_SUBSEC_TIME_ORIG, "SubSecTimeOriginal")
            map.put(ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, "SubSecTimeOriginal")
            map.put(ExifInterface.TAG_SUBJECT_AREA, "SubjectArea")
            map.put(ExifInterface.TAG_SUBJECT_DISTANCE, "SubjectDistance")
            map.put(ExifInterface.TAG_SUBJECT_DISTANCE_RANGE, "SubjectDistanceRange")
            map.put(ExifInterface.TAG_SUBJECT_LOCATION, "SubjectLocation")
            map.put(ExifInterface.TAG_USER_COMMENT, "UserComment")
            map.put(ExifInterface.TAG_WHITE_BALANCE, "WhiteBalance")
            map.put(ExifInterface.TAG_GPS_ALTITUDE, "GPSAltitude")
            map.put(ExifInterface.TAG_GPS_ALTITUDE_REF, "GPSAltitudeRef")
            map.put(ExifInterface.TAG_GPS_AREA_INFORMATION, "GPSAreaInformation")
            map.put(ExifInterface.TAG_GPS_DOP, "GPSDOP")
            map.put(ExifInterface.TAG_GPS_DATESTAMP, "GPSDateStamp")
            map.put(ExifInterface.TAG_GPS_DEST_BEARING, "GPSDestBearing")
            map.put(ExifInterface.TAG_GPS_DEST_BEARING_REF, "GPSDestBearingRef")
            map.put(ExifInterface.TAG_GPS_DEST_DISTANCE, "GPSDestDistance")
            map.put(ExifInterface.TAG_GPS_DEST_DISTANCE_REF, "GPSDestDistanceRef")
            map.put(ExifInterface.TAG_GPS_DEST_LATITUDE, "GPSDestLatitude")
            map.put(ExifInterface.TAG_GPS_DEST_LATITUDE_REF, "GPSDestLatitudeRef")
            map.put(ExifInterface.TAG_GPS_DEST_LONGITUDE, "GPSDestLongitude")
            map.put(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF, "GPSDestLongitudeRef")
            map.put(ExifInterface.TAG_GPS_DIFFERENTIAL, "GPSDifferential")
            map.put(ExifInterface.TAG_GPS_IMG_DIRECTION, "GPSImgDirection")
            map.put(ExifInterface.TAG_GPS_IMG_DIRECTION_REF, "GPSImgDirectionRef")
            map.put(ExifInterface.TAG_GPS_LATITUDE, "GPSLatitude")
            map.put(ExifInterface.TAG_GPS_LATITUDE_REF, "GPSLatitudeRef")
            map.put(ExifInterface.TAG_GPS_LONGITUDE, "GPSLongitude")
            map.put(ExifInterface.TAG_GPS_LONGITUDE_REF, "GPSLongitudeRef")
            map.put(ExifInterface.TAG_GPS_MAP_DATUM, "GPSMapDatum")
            map.put(ExifInterface.TAG_GPS_MEASURE_MODE, "GPSMeasureMode")
            map.put(ExifInterface.TAG_GPS_PROCESSING_METHOD, "GPSProcessingMethod")
            map.put(ExifInterface.TAG_GPS_SATELLITES, "GPSSatellites")
            map.put(ExifInterface.TAG_GPS_SPEED, "GPSSpeed")
            map.put(ExifInterface.TAG_GPS_SPEED_REF, "GPSSpeedRef")
            map.put(ExifInterface.TAG_GPS_STATUS, "GPSStatus")
            map.put(ExifInterface.TAG_GPS_TIMESTAMP, "GPSTimeStamp")
            map.put(ExifInterface.TAG_GPS_TRACK, "GPSTrack")
            map.put(ExifInterface.TAG_GPS_TRACK_REF, "GPSTrackRef")
            map.put(ExifInterface.TAG_GPS_VERSION_ID, "GPSVersionID")
            map.put(ExifInterface.TAG_INTEROPERABILITY_INDEX, "InteroperabilityIndex")
            map.put(ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH, "ThumbnailImageLength")
            map.put(ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH, "ThumbnailImageWidth")
            map.put(ExifInterface.TAG_DNG_VERSION, "DNGVersion")
            map.put(ExifInterface.TAG_DEFAULT_CROP_SIZE, "DefaultCropSize")
            map.put(ExifInterface.TAG_ORF_THUMBNAIL_IMAGE, "ThumbnailImage")
            map.put(ExifInterface.TAG_ORF_PREVIEW_IMAGE_START, "PreviewImageStart")
            map.put(ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH, "PreviewImageLength")
            map.put(ExifInterface.TAG_ORF_ASPECT_FRAME, "AspectFrame")
            map.put(ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER, "SensorBottomBorder")
            map.put(ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER, "SensorLeftBorder")
            map.put(ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER, "SensorRightBorder")
            map.put(ExifInterface.TAG_RW2_SENSOR_TOP_BORDER, "SensorTopBorder")
            map.put(ExifInterface.TAG_RW2_ISO, "ISO")
            map.put(ExifInterface.TAG_RW2_JPG_FROM_RAW, "JpgFromRaw")

            tags = map
        }
    }
}

//fun MetaData.Companion.tags: Map<String, String> = HashMap<String, String>()