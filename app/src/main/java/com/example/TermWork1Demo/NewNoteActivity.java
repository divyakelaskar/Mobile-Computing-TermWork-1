package com.example.TermWork1Demo;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NewNoteActivity extends AppCompatActivity {

    private EditText todoTitle, todoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        todoTitle = findViewById(R.id.noteTitle);
        todoDescription = findViewById(R.id.noteDescription);
    }

    @SuppressLint("MissingPermission")
    public void onSubmitClick(View view) {
        // Get the title and description from EditText fields
        String title = todoTitle.getText().toString();
        String description = todoDescription.getText().toString();


        if (!title.isEmpty()) {
            // Create an intent to open the NoteDetailActivity when the notification is clicked
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            // Prepare data to send back to MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("description", description);
            setResult(RESULT_OK, resultIntent);

            // Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "note_channel_id")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("New note created 🎉 ")
                    //.setContentText("Title : " + title)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            // Show the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());

            // Finish this activity to go back to the main activity
            finish();
        }
    }

}
