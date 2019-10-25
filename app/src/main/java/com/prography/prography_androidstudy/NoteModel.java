package com.prography.prography_androidstudy;

import android.content.Context;

import java.util.ArrayList;

public class NoteModel {
    private NotesDao notesDao;

    public NoteModel(Context context) {
        notesDao = NoteDatabase.getDatabase(context).notesDao();
    }

    public boolean addNote(String title, String description, String date, String time) {
        final Note newNote = new Note(title, description, date, time);
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDao.insert(newNote);
            }
        });
        addThread.start();

        return true;
    }

    public boolean addNote(String title, String description, String date, String time, int id) {
        final Note note = getNote(id);
        note.setDate(date);
        note.setTime(time);
        note.setTitle(title);
        note.setDescription(description);
        Thread editThread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDao.insert(note);
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
                noteArrayList.addAll(notesDao.findAllNotes());
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
                note.addAll(notesDao.findNoteswithID(id));
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

    public boolean deleteNote(int id) {
        final Note note = getNote(id);
        Thread deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDao.DeleteNotes(note);
            }
        });
        deleteThread.start();

        try {
            deleteThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    public ArrayList<Note> getNote(final String word) {
        final ArrayList<Note> noteArrayList = new ArrayList<>();
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteArrayList.addAll(notesDao.findNoteswithTitle(word));
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
}
