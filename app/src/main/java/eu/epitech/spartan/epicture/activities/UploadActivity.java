package eu.epitech.spartan.epicture.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eu.epitech.spartan.epicture.R;

public class UploadActivity extends AppCompatActivity {

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

        // disable upload and clear buttons
        Button bUpload = findViewById(R.id.uploadButton);
        Button bClear = findViewById(R.id.clearButton);
        bUpload.setEnabled(false);
        bClear.setEnabled(false);
    }
}
