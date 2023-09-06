package com.yousef.to_do;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<Note> noteList;
    private final OnItemClickListener listener;

    public NoteAdapter( List<Note> noteList, OnItemClickListener listener) {
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Note note = noteList.get(position);
        holder.textViewNoteName.setText(note.getName());
        holder.textViewNoteBody.setText(note.getBody());

        // Set an OnClickListener for the item view
        holder.itemView.setOnClickListener(v -> {
            // Call the onItemClick method of the listener
            listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(ArrayList<Note> newList)
    {
        noteList.clear();
        noteList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNoteName;
        public TextView textViewNoteBody;

        public NoteViewHolder(View itemView) {
            super(itemView);
            textViewNoteName = itemView.findViewById(R.id.textViewNoteName);
            textViewNoteBody = itemView.findViewById(R.id.textViewNoteBody);
        }
    }
    // Define an interface to handle item clicks
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
