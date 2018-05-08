package com.vjc.imagecompare;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.vjc.imagecompare.Model.MetaData;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/** Fragment containing the ImageInspectorToolbar and the ImageInspectorView */
public class ImageInspectorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_inspector, container, false);

        ImageButton addButton = (ImageButton)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonAction();
            }
        });

        final ImageButton cameraButton = (ImageButton)view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraButtonAction();
            }
        });

        ImageButton detailsButton = (ImageButton)view.findViewById(R.id.detailsButton);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsButtonAction();
            }
        });

        /** get the reference to the ImageInspectorView instance of this fragment */
        _imageInspectorView = (ImageInspectorView)view.findViewById(R.id.imageInspectorView);

        _imageDetailsView = new ImageDetailsView(getContext());
        return view;
    }


    void addButtonAction() {
        Log.d("ImageInspectorFragment", "addButtonAction");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_CODE_IMAGE_GALLERY);
//        _imageInspectorView.setBitmap(null);
    }

    void cameraButtonAction() {
        Log.d("ImageInspectorFragment", "cameraButtonAction");

        /// Support for API 15
        if (this.getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

        } else {

        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            _temporaryImageFile = File.createTempFile("temp", ".jpg", dir);
        } catch (IOException exception) {
            _temporaryImageFile = null;
        }

        if (_temporaryImageFile != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, _temporaryImageFile.toURI());
//            startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
        }

        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
    }

    void detailsButtonAction() {
        Log.d("ImageInspectorFragment", "detailsButtonAction");
//        this.showImageDetailsView();

        Context ctx = getContext();
        if (_overlay == null) {


            if (ctx == null) {
                return;
            }
            _overlay = new Overlay(ctx);
        }

        _overlay.show(_imageDetailsView, _imageInspectorView);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_IMAGE_GALLERY:
                if (data != null) {
                    Uri imageURI = data.getData();

                    try {
                        //* retrieve and set the bitmap for the _imageIspectorView
                        Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageURI);
                        _imageInspectorView.setBitmap(bm);

                        //* set the MetaData array for the _imageDetailsView
                        try {
                            List<MetaData> metaDataArray = MetaData.metaDataArrayFrom(imageURI, getContext());
                            _imageDetailsView.setData(metaDataArray.toArray(new MetaData[metaDataArray.size()]));
                        } catch (MetaData.MetaDataException e){
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        Log.e(null, "unable to create bitmap");
                    }
//                    catch (ImageProcessingException e) {
//                        Log.e(null, "unable to process information");
//                    }

                }
                break;
            case REQUEST_CODE_IMAGE_CAMERA:
                /** can do by fetching Bitmap directly */
//                Bitmap bm = (Bitmap) data.getExtras().get("data");
//                try {
//                    Bitmap bm = (Bitmap)MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(_temporaryImageFile.toURI().toString()));
//                } catch (IOException exception) {
//
//                }

                Bitmap bm = (Bitmap) data.getExtras().get("data");
                _imageInspectorView.setBitmap(bm);

                break;

            default:
                break;
        }

    }








    /** Request code to use to fetch image from gallery (Used in startActivityForResult) */
    private final int       REQUEST_CODE_IMAGE_GALLERY = 1;

    /** Request code to use to fetch image by camera capture */
    private final int       REQUEST_CODE_IMAGE_CAMERA = 2;

    /** The ImageInspectorView instance this fragment manages. CREATED IN LAYOUT*/
    private ImageInspectorView      _imageInspectorView = null;

    /** file used to store temporary images (from camera capture) */
    private File                    _temporaryImageFile = null;

    /** the view used to show image details. CREATED IN THIS FILE */
    @NonNull private ImageDetailsView        _imageDetailsView = null;

    /** to show the Image details as overlay */
    private Overlay                 _overlay;



    /** Adds the ImageDetailsView instance over the ImageInspectorView */
    private void showImageDetailsView() {
        Context ctx = getContext();
        if (ctx == null) {
            return;
        }

        if (_imageDetailsView == null) {
//            _imageDetailsView = new ImageDetailsView(ctx);
        } else if (_imageDetailsView.getParent() != null) {
            this.hideImageDetailsView();
            return;
        }

        _imageInspectorView.removeView(_imageDetailsView);
        _imageInspectorView.addView(_imageDetailsView);

        ViewGroup.LayoutParams params = _imageDetailsView.getLayoutParams();
        params.width = (int)(_imageInspectorView.getWidth() * 0.75f);
        params.height = (int)(_imageInspectorView.getHeight() * 0.75f);

        _imageDetailsView.setLayoutParams(params);


        _imageDetailsView.post(new Runnable() {
            @Override
            public void run() {
                Functions.centerView(_imageDetailsView);
            }
        });

        _imageDetailsView.requestLayout();

//        Functions.centerView(_imageDetailsView, _imageInspectorView);

    }

    /** hides the ImageDetails View */
    private void hideImageDetailsView() {
        _imageInspectorView.removeView(_imageDetailsView);
    }

}
