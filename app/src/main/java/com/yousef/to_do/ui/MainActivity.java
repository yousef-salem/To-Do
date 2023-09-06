package com.yousef.to_do.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yousef.to_do.Note;
import com.yousef.to_do.NoteAdapter;
import com.yousef.to_do.NoteDatabaseHelper;
import com.yousef.to_do.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {
    private NoteAdapter noteAdapter;
    private ArrayList<Note> noteList;
    NoteDatabaseHelper databaseHelper = new NoteDatabaseHelper(MainActivity.this);


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Base_Theme_ToDo);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

        // Initialize your database and populate noteList with data
        databaseHelper = new NoteDatabaseHelper(this);
        noteList.addAll(databaseHelper.getAllNotes());

        FloatingActionButton fabAddNote = findViewById(R.id.fabAddNote);
        fabAddNote.setOnClickListener(view -> {
            // Create a custom dialog for adding a new note
            final Dialog addNoteDialog = new Dialog(MainActivity.this);
            addNoteDialog.setContentView(R.layout.dialog_add_note);

            // Initialize dialog views
            final EditText editTextNoteName = addNoteDialog.findViewById(R.id.editTextNoteName);
            final EditText editTextNoteBody = addNoteDialog.findViewById(R.id.editTextNoteBody);
            Button buttonSave = addNoteDialog.findViewById(R.id.buttonSave);
            Button buttonCancel = addNoteDialog.findViewById(R.id.buttonCancel);

            // Handle the "Save" button click
            buttonSave.setOnClickListener(v -> {
                String noteName = editTextNoteName.getText().toString().trim();
                String noteBody = editTextNoteBody.getText().toString().trim();

                if (!noteName.isEmpty() && !noteBody.isEmpty()) {
                    // Create a new Note object and add it to the database
                    Note newNote = new Note(noteName, noteBody);
                    long newNoteId = databaseHelper.addNote(newNote);

                    if (newNoteId != -1) {
                        // Add the new note to the list and refresh the RecyclerView
                        newNote.setId((int) newNoteId);
                        noteList.add(newNote);
                        noteAdapter.notifyDataSetChanged();
                    }

                    addNoteDialog.dismiss(); // Close the dialog
                } else {
                    // Show an error message if any fields are empty
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });

            // Handle the "Cancel" button click
            buttonCancel.setOnClickListener(v -> {
                addNoteDialog.dismiss(); // Close the dialog
            });

            // Show the dialog
            addNoteDialog.show();
        });

    }

    @Override
    public void onItemClick(int position) {
        // Handle item click here
        openNoteDetailActivity(position);
    }

    private void openNoteDetailActivity(int position) {
        Intent intent = new Intent(this, NoteDetailActivity.class);
        intent.putExtra("NOTE_ID", noteList.get(position).getId());
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data in the RecyclerView
        noteList.clear();
        noteList.addAll(databaseHelper.getAllNotes());
        noteAdapter.notifyDataSetChanged();
    }


}