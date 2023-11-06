package com.example.TermWork1Demo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NoteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity_detail);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");

            if (title != null && description != null) {
                // Display the title and description in your UI elements
                TextView titleTextView = findViewById(R.id.titleTextView);
                TextView descriptionTextView = findViewById(R.id.descriptionTextView);

                titleTextView.setText(title);
                descriptionTextView.setText(description);
            }
        }
    }

}