package eu.epitech.spartan.epicture.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import eu.epitech.spartan.epicture.R;
import eu.epitech.spartan.epicture.modele.Picture;
import eu.epitech.spartan.epicture.services.HandlerService;

public class UploadActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 1;
    private Picture pic;

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

        handleTextErrors();
    }

    /**
     * help user browse through storage pictures to choose one
     */
    private void choosePictureToUpload() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    /**
     * upload selected pictures
     */
    private void uploadPictures() {
        ImageView imgView = findViewById(R.id.toUploadImgView);
        EditText titleView = findViewById(R.id.toUploadTitle);
        EditText descriptionView = findViewById(R.id.toUploadDescription);

        //up.image = image;
        pic.setTitle(titleView.getText().toString());
        pic.setDescription(descriptionView.getText().toString());
        if (pic.getTitle().trim().equals(""))
            titleView.setError("Title can not be empty");
        else {
            // upload image with title and description
            HandlerService.startActionUpload(getApplicationContext(), pic.getImage(), pic.getTitle(), pic.getDescription());

            // go back to main activity while service runs in the background
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    /**
     * removes selected pictures
     */
    private void clearPictures() {
        // remove pictures
        ImageView imageView = findViewById(R.id.toUploadImgView);
        EditText titleView = findViewById(R.id.toUploadTitle);
        EditText descriptionView = findViewById(R.id.toUploadDescription);
        imageView.setImageURI(null);
        pic = null;
        titleView.setText("");
        titleView.setError(null);
        descriptionView.setText("");
        descriptionView.setError(null);

        // disable upload and clear buttons
        Button bUpload = findViewById(R.id.uploadButton);
        Button bClear = findViewById(R.id.clearButton);
        LinearLayout addPicLayout = findViewById(R.id.toChooseImgAction);
        bUpload.setEnabled(false);
        bClear.setEnabled(false);
        addPicLayout.setVisibility(View.VISIBLE);
    }

    /**
     * add chosen picture to the list of selected pictures
     */
    private void addPicture(Uri selectedImage) {
        ImageView imageView = findViewById(R.id.toUploadImgView);
        imageView.setImageURI(selectedImage);
        pic = new Picture(selectedImage);

        // enable upload and clear buttons
        Button bUpload = findViewById(R.id.uploadButton);
        Button bClear = findViewById(R.id.clearButton);
        LinearLayout addPicLayout = findViewById(R.id.toChooseImgAction);
        bUpload.setEnabled(true);
        bClear.setEnabled(true);
        addPicLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            addPicture(selectedImage);
        }
    }

    private void handleTextErrors() {
        final EditText titleView = findViewById(R.id.toUploadTitle);
        final EditText descriptionView = findViewById(R.id.toUploadDescription);

        titleView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!titleView.getText().toString().trim().equals("")) {
                    titleView.setError(null);
                }
            }
        });
    }
}
