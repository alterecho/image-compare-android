package com.example.vijay.myapplication;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.Random;

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

        _imageInspectorView = (ImageInspectorView)view.findViewById(R.id.imageInspectorView);

        return view;
    }


    void addButtonAction() {
        Log.d("ImageInspectorFragment", "addButtonAction");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

//        startActivityForResult(intent, REQUEST_CODE_GALLERY_IMAGE);
        _imageInspectorView.setBitmap(null);
    }

    void cameraButtonAction() {
        Log.d("ImageInspectorFragment", "cameraButtonAction");
    }

    void detailsButtonAction() {
        Log.d("ImageInspectorFragment", "detailsButtonAction");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_GALLERY_IMAGE:
                if (data != null) {
                    Uri imageURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageURI);

                        ImageInspectorView imageInspectorView = (ImageInspectorView)getView().findViewById(R.id.imageInspectorView);
                        imageInspectorView.setBitmap(bitmap);


                    } catch (IOException e) {
                        Log.e(null, "unable to create bitmap");
                    }

                }

            default:
                break;
        }

    }



    /** Request code to use to fetch image from gallery (Used in startActivityForResult) */
    private final int REQUEST_CODE_GALLERY_IMAGE = 1;

    /** The ImageInspectorView instance this fragment manages */
    private ImageInspectorView _imageInspectorView = null;

}
