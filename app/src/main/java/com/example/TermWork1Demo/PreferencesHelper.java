package com.example.TermWork1Demo;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PreferencesHelper {
    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String NOTE_LIST_KEY = "noteList";

    private SharedPreferences sharedPreferences;

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveNoteList(List<Note> noteList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        editor.putString(NOTE_LIST_KEY, json);
        editor.apply();
    }

    public List<Note> getNoteList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(NOTE_LIST_KEY, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        } else {
            return gson.fromJson(json, new TypeToken<List<Note>>() {}.getType());
        }
    }
}


