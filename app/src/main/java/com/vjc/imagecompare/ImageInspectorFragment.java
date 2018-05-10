package com.vjc.imagecompare;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.vjc.imagecompare.Model.MetaData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static android.app.Activity.RESULT_OK;

/** Fragment containing the ImageInspectorToolbar and the ImageInspectorView */
public class ImageInspectorFragment extends Fragment implements ImageDetailsView.Listener {

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
        _imageDetailsView.setDelegate(this);

        return view;
    }

    @Override
    public void closeClicked(ImageDetailsView view) {
        _overlay.hide();
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
//        if (this.getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
//
//        } else {
//
//        }


        _tempFile = getContext().getExternalFilesDir(null);


        if (_tempFile != null) {
            try {
                _tempFile = File.createTempFile("tmp", ".jpg", _tempFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri = Uri.fromFile(_tempFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
            } catch (IOException e) {
                e.printStackTrace();
                _tempFile = null;
            }
        } else {

        }

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

                    AtomicReference<List<MetaData>> metaDataArrayRef = new AtomicReference<>();
                    _imageInspectorView.setBitmapFrom(imageURI, metaDataArrayRef);
                    _imageDetailsView.setData(metaDataArrayRef.get().toArray(new MetaData[metaDataArrayRef.get().size()]));

                }
                break;
            case REQUEST_CODE_IMAGE_CAMERA:
                if (_tempFile != null) {
                    Uri imageUri = Uri.fromFile(_tempFile);
                    AtomicReference<List<MetaData>> metaDataArrayRef = new AtomicReference<>();
                    _imageInspectorView.setBitmapFrom(imageUri, metaDataArrayRef);
                    _imageDetailsView.setData(metaDataArrayRef.get().toArray(new MetaData[metaDataArrayRef.get().size()]));
                }
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
    private File             _tempFile;

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
