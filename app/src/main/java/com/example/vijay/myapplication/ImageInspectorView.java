package com.example.vijay.myapplication;
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

import java.io.IOException;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class ImageInspectorView extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_inspector, container, false);
        Random rnd = new Random();
        view.setBackgroundColor(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));

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

        return view;
    }


    void addButtonAction() {
        Log.d("ImageInspectorView", "addButtonAction");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE);
    }

    void cameraButtonAction() {
        Log.d("ImageInspectorView", "cameraButtonAction");
    }

    void detailsButtonAction() {
        Log.d("ImageInspectorView", "detailsButtonAction");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case GALLERY_IMAGE_REQUEST_CODE:
                if (data != null) {
                    Uri imageURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageURI);
                        if (_imageView == null) {
                            _imageView = new ImageView(getContext());
                            
                            _imageView.setLayoutParams(layoutParams);
                        }

                    } catch (IOException e) {
                        Log.e(null, "unable to create bitmap");
                    }

                }

            default:
                break;
        }

    }

    /** */
    private final int GALLERY_IMAGE_REQUEST_CODE = 1;

    private ImageView _imageView = null;
}
