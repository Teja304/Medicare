package com.tejaandroid.medicare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrescribeActivity extends AppCompatActivity {

    TextView tv;
    FloatingActionButton fab;
    ImageButton back;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;
    File file;
    private File[] listFile;
    private String[] FilePathStrings;
    GridView grid;
    GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescribe);

        tv = findViewById(R.id.textviewpre);

        back = findViewById(R.id.backbtn);
        back.setOnClickListener(v -> finish());

        Log.d("Medicare", ""+getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath());
        if (file.isDirectory())
        {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            for (int i = 0; i < listFile.length; i++)
            {
                FilePathStrings[i] = listFile[i].getAbsolutePath();
            }
        }
       grid = findViewById(R.id.gridView);
       adapter = new GridViewAdapter(this, FilePathStrings);
       grid.setAdapter(adapter);
       grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick (AdapterView<?> parent, View view,
                                    int position, long id)
           {
               Intent i1 = new Intent(getApplicationContext(), ImageViewActivity.class);
               i1.putExtra("Image", FilePathStrings[position]);
               startActivity(i1);

           }
       });

        if(FilePathStrings.length == 0)
            grid.setVisibility(View.INVISIBLE);
        else
            tv.setVisibility(View.INVISIBLE);

        fab = findViewById(R.id.newpre);
        fab.setOnClickListener(v -> dispatchTakePictureIntent());
    }

    private File createImageFile() throws IOException {
        // Create an image file name
       String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d("Medicare", "createImageFile() successful!");
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("Medicare", "createImageFile unsuccessful : " + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Medicare", "Prescribe activity resumed");
        file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath());
        if (file.isDirectory())
        {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            for (int i = 0; i < listFile.length; i++)
            {
                FilePathStrings[i] = listFile[i].getAbsolutePath();
            }
        }
        grid = findViewById(R.id.gridView);
        adapter = new GridViewAdapter(this, FilePathStrings);
        grid.setAdapter(adapter);
    }

}