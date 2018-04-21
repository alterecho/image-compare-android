package com.example.vijay.myapplication;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Random;

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
    }

    void cameraButtonAction() {
        Log.d("ImageInspectorView", "cameraButtonAction");
    }

    void detailsButtonAction() {
        Log.d("ImageInspectorView", "detailsButtonAction");
    }

}
