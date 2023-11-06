package com.example.TermWork1Demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Define a request code for starting the NewNoteActivity
    private static final int REQUEST_CODE_NEW_TODO = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "note_channel_id";
    private ArrayList<Note> notesList;
    private ArrayAdapter<Note> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a notification channel
        createNotificationChannel();

        ListView noteListView = findViewById(R.id.noteListView);
        notesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                notesList);
        noteListView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.addnoteButton);
        fab.setOnClickListener(view -> {
            // Start the NewNoteActivity and wait for a result
            Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
            startActivityForResult(intent, REQUEST_CODE_NEW_TODO);
        });


        noteListView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected task from the noteList based on the position
            Note selectedNote = notesList.get(position);

            // Create an explicit intent to start TaskDetailActivity
            Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);

            // Pass title and description as separate extras in the intent
            intent.putExtra("title", selectedNote.getTitle());
            intent.putExtra("description", selectedNote.getDescription());

            startActivity(intent);
        });

        // Retrieve the list of notes from SharedPreferences
        PreferencesHelper preferencesHelper = new PreferencesHelper(this);
        List<Note> savedNotes = preferencesHelper.getNoteList();
        notesList.addAll(savedNotes);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = NOTIFICATION_CHANNEL_ID;
            CharSequence channelName = "Note Channel";
            String channelDescription = "Notifications for Note App";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_NEW_TODO && resultCode == RESULT_OK && data != null) {
            // Get the title and description from the result
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");

            Note newNote = new Note(title, description);

            // Add the note to the noteList
            if (title != null && !title.isEmpty()) {
                notesList.add(newNote);
                adapter.notifyDataSetChanged();
            }

            // Save the updated list of notes to SharedPreferences
            PreferencesHelper preferencesHelper = new PreferencesHelper(this);
            preferencesHelper.saveNoteList(notesList);
        }
    }
}
