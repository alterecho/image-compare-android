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

public class ImageInspectorFragment extends Fragment {

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
        Log.d("ImageInspectorFragment", "addButtonAction");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

//        startActivityForResult(intent, REQUEST_CODE_GALLERY_IMAGE);
//
        initializeImageView();

        int width = getView().getWidth();
        int height = getView().getHeight();

        _imageView.getLayoutParams().width = 100;
        _imageView.getLayoutParams().height = 100;
        _imageView.setX((int)(width * 0.5));
        _imageView.setY((int)(height * 0.5));
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

                        initializeImageView();

                        int imageWidth = bitmap.getWidth();
                        int imageHeight = bitmap.getHeight();

                        _imageView.getLayoutParams().width = imageWidth;
                        _imageView.getLayoutParams().height = imageHeight;
                        _imageView.setImageBitmap(bitmap);


                    } catch (IOException e) {
                        Log.e(null, "unable to create bitmap");
                    }

                }

            default:
                break;
        }

    }

    /** initializes _imageView, and adds and centers it in the imageInspectorView (RelativeLayout) */
    private final void initializeImageView() {
        if (_imageView == null) {
            _imageView = new ImageView(getContext());
            _imageView.setBackgroundColor(Color.RED);

            RelativeLayout layout = this.getView().findViewById(R.id.imageInspectorView);
            layout.addView(_imageView);
        }
    }

    private final int REQUEST_CODE_GALLERY_IMAGE = 1;

    private ImageView _imageView = null;
}
