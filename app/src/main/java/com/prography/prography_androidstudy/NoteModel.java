package com.prography.prography_androidstudy;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoteModel {
    private NoteDatabase db;
    private NotesDao notesDao;

    public NoteModel(Context context) {
        db = NoteDatabase.getDatabase(context);
        notesDao = db.notesDao();
    }

    public boolean addNote(String title, String description) {
        Date date = new Date(System.currentTimeMillis());
        String today = new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault()).format(date);
        final Note newNote = new Note(title, description, today);
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.notesDao().insert(newNote);
            }
        });
        addThread.start();

        return true;
    }

    public boolean addNote(String title, String description, int id) {
        Date date = new Date(System.currentTimeMillis());
        String today = new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault()).format(date);
        final Note note = getNote(id);
        note.setDate(today);
        note.setTitle(title);
        note.setDescription(description);
        Thread editThread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.notesDao().insert(note);
            }
        });
        editThread.start();

        return true;
    }

    public ArrayList<Note> getAllNote() {
        final ArrayList<Note> noteArrayList = new ArrayList<>();
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteArrayList.addAll(db.notesDao().findAllNotes());
            }
        });
        loadThread.start();

        try {
            loadThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return noteArrayList;
    }

    public Note getNote(final int id) {
        final ArrayList<Note> note = new ArrayList<>();
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                note.addAll(db.notesDao().findNoteswithID(id));
            }
        });
        loadThread.start();

        try {
            loadThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return note.get(0);
    }
}
