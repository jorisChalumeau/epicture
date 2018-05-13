package eu.epitech.spartan.epicture.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eu.epitech.spartan.epicture.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = findViewById(R.id.buttonDispMode);
        Button b2 = findViewById(R.id.buttonUploadMode);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispReadContentActivity();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispUploadActivity();
            }
        });
    }

    private void dispReadContentActivity() {
        Intent intent = new Intent(this, ReadContentActivity.class);
        startActivity(intent);
    }

    private void dispUploadActivity() {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }
}
