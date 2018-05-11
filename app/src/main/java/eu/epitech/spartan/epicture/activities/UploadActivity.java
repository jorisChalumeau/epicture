package eu.epitech.spartan.epicture.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import eu.epitech.spartan.epicture.R;

public class UploadActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        FloatingActionButton bSelector = findViewById(R.id.fabChooseImg);
        Button bUpload = findViewById(R.id.uploadButton);
        Button bClear = findViewById(R.id.clearButton);

        bSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePictureToUpload();
            }
        });
        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPictures();
            }
        });
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPictures();
            }
        });
    }

    private void choosePictureToUpload() {
        // TODO : browse through storage pictures
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        // ------------ on select -------------
        // enable upload and clear buttons
        Button bUpload = findViewById(R.id.uploadButton);
        Button bClear = findViewById(R.id.clearButton);
        if(!bUpload.isEnabled()) {
            bUpload.setEnabled(true);
            bClear.setEnabled(true);
        }

    }

    /**
     * upload selected pictures
     */
    private void uploadPictures() {
        // TODO : upload pictures on imgur using user token and imgur api (and retrofit ?)
    }

    /**
     * removes all selected pictures
     */
    private void clearPictures() {
        // TODO : remove pictures
        ImageView imageView = findViewById(R.id.toUploadImgView);
        imageView.setImageBitmap(null);
        // disable upload and clear buttons
        Button bUpload = findViewById(R.id.uploadButton);
        Button bClear = findViewById(R.id.clearButton);
        bUpload.setEnabled(false);
        bClear.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ImageView imageView = findViewById(R.id.toUploadImgView);
                imageView.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
