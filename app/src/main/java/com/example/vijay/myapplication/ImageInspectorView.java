package com.example.vijay.myapplication;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

public class ImageInspectorView extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_inspector, container, false);
        Random rnd = new Random();
        view.setBackgroundColor(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));

        return view;
    }
}
