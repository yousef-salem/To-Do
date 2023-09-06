package com.yousef.to_do.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yousef.to_do.Note;
import com.yousef.to_do.NoteDatabaseHelper;
import com.yousef.to_do.R;

public class EditNoteActivity extends AppCompatActivity {
    private EditText editTextNoteName;
    private EditText editTextNoteBody;
    private int noteId;
    NoteDatabaseHelper databaseHelper = new NoteDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Base_Theme_ToDo);
        setContentView(R.layout.activity_edit_note);

        editTextNoteName = findViewById(R.id.editTextNoteName);
        editTextNoteBody = findViewById(R.id.editTextNoteBody);

        // Retrieve the noteId from the Intent
        noteId = getIntent().getIntExtra("NOTE_ID", -1);

        if (noteId != -1) {
            // Fetch the note details from the database using the noteId

            Note note = databaseHelper.getNoteById(noteId);

            if (note != null) {
                // Populate the EditText fields with the note's details
                editTextNoteName.setText(note.getName());
                editTextNoteBody.setText(note.getBody());
            }
        }

        // Handle the "Save" button click to update the note
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> {
            // Get the edited note name and body
            String updatedNoteName = editTextNoteName.getText().toString().trim();
            String updatedNoteBody = editTextNoteBody.getText().toString().trim();

            if (!updatedNoteName.isEmpty() && !updatedNoteBody.isEmpty()) {
                // Update the note in the database
                Note updatedNote = new Note(updatedNoteName, updatedNoteBody);
                updatedNote.setId(noteId);

                if (databaseHelper.updateNote(updatedNote) == 1) {
                    // Update successful
                    Toast.makeText(EditNoteActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity and return to the details activity
                } else {
                    // Update failed
                    Toast.makeText(EditNoteActivity.this, "Failed to update note", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Show an error message if any fields are empty
                Toast.makeText(EditNoteActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current values of editTextNoteName and editTextNoteBody
        outState.putString("EDIT_TEXT_NOTE_NAME", editTextNoteName.getText().toString());
        outState.putString("EDIT_TEXT_NOTE_BODY", editTextNoteBody.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore the values of editTextNoteName and editTextNoteBody
        String savedNoteName = savedInstanceState.getString("EDIT_TEXT_NOTE_NAME", "");
        String savedNoteBody = savedInstanceState.getString("EDIT_TEXT_NOTE_BODY", "");

        editTextNoteName.setText(savedNoteName);
        editTextNoteBody.setText(savedNoteBody);
    }
}
