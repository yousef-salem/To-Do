package com.yousef.to_do.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.yousef.to_do.Note;
import com.yousef.to_do.NoteDatabaseHelper;
import com.yousef.to_do.R;

public class NoteDetailActivity extends AppCompatActivity {
    private TextView textViewNoteName;
    private TextView textViewNoteBody;
    NoteDatabaseHelper databaseHelper;
    Note note;
    int noteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Base_Theme_ToDo);
        setContentView(R.layout.activity_note_detail);

        textViewNoteName = findViewById(R.id.textViewNoteName);
        textViewNoteBody = findViewById(R.id.textViewNoteBody);
        Button buttonEditNote = findViewById(R.id.buttonEditNote);
        Button buttonDeleteNote = findViewById(R.id.buttonDeleteNote);

        // Retrieve the selected note's ID from the Intent
        noteId = getIntent().getIntExtra("NOTE_ID", -1);

        if (noteId != -1) {
            // Fetch the note details from the database using the noteId
            databaseHelper = new NoteDatabaseHelper(this);
            note = databaseHelper.getNoteById(noteId);

            if (note != null) {
                // Display the note details in the TextViews
                setNotApear(note.getName(),note.getBody());

                // Handle editing and deleting notes
                buttonEditNote.setOnClickListener(v -> {
                    // Handle editing the note
                    Intent editIntent = new Intent(NoteDetailActivity.this, EditNoteActivity.class);
                    editIntent.putExtra("NOTE_ID", noteId);
                    startActivity(editIntent);
                });

                buttonDeleteNote.setOnClickListener(v -> {
                    // Handle deleting the note
                    databaseHelper.deleteNote(noteId);
                    finish();
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update note in edittext when it is edited
        note = databaseHelper.getNoteById(noteId);
        setNotApear(note.getName(),note.getBody());

    }


    void setNotApear(String note , String body){
        // Make note showed in EditText
        textViewNoteName.setText(note);
        textViewNoteBody.setText(body);
    }
}