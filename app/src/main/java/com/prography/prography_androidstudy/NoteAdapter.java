package com.prography.prography_androidstudy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private ArrayList<Note> notes;
    private LayoutInflater layoutInflater;

    public NoteAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_note, parent, false);
        NoteAdapter.ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        if (note != null) {
            holder.tvNoteTitle.setText(note.getTitle());
            holder.tvNoteDescription.setText(note.getDescription());
            holder.tvNoteDate.setText(note.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle;
        TextView tvNoteDescription;
        TextView tvNoteDate;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            /* findViewByID */
            tvNoteTitle = itemView.findViewById(R.id.note_title);
            tvNoteDescription = itemView.findViewById(R.id.note_description);
            tvNoteDate = itemView.findViewById(R.id.note_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent newIntent = new Intent(itemView.getContext(), AddNoteActivity.class);
                        newIntent.putExtra("noteId", notes.get(pos).getId());
                        itemView.getContext().startActivity(newIntent);
                    }
                }
            });
        }
    }
}
