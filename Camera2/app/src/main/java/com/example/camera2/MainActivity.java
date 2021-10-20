package com.example.camera2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView imageView;

    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    private static final int REQUEST_ID_VIDEO_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonImage = (Button) findViewById(R.id.button_image);
        Button buttonVideo = (Button) findViewById(R.id.button_video);
        videoView = (VideoView) findViewById(R.id.videoView);
        imageView = (ImageView) findViewById(R.id.imageView);

        buttonImage.setOnClickListener(v -> {  // Create an implicit intent, for image capture.
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Start camera and wait for the results.
            startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);});

        buttonVideo.setOnClickListener(v -> {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_ID_VIDEO_CAPTURE);
            }
        });
    }



    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bp);
        } else if (requestCode == REQUEST_ID_VIDEO_CAPTURE) {
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }

}