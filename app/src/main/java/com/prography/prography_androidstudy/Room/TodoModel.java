package com.prography.prography_androidstudy.Room;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.prography.prography_androidstudy.src.common.utils.NotifyWorker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TodoModel {
    private static int[] amountMin = {0, 10, 20, 30};
    private TodosDao todosDao;

    public TodoModel(Context context) {
        todosDao = TodoDatabase.getDatabase(context).notesDao();
    }

    public int addNote(String title, String description, Date dateTime, boolean alarm0min, boolean alarm10min, boolean alarm30min, boolean alarm60min) {
        if (title.equals("") || description.equals("")) {
            return -1;
        }
        final Todo newTodo = new Todo(title, description, dateTime, alarm0min, alarm10min, alarm30min, alarm60min);
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                todosDao.insert(newTodo);
            }
        });
        addThread.start();
        try {
            addThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return newTodo.getId();
    }

    public int addNote(String title, String description, Date dateTime, boolean alarm0min, boolean alarm10min, boolean alarm30min, boolean alarm60min, int id) {
        if (title.equals("") || description.equals("")) {
            return -1;
        }
        final Todo todo = findNote(id);
        todo.setDateTime(dateTime);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setAlarm0min(alarm0min);
        todo.setAlarm10min(alarm10min);
        todo.setAlarm30min(alarm30min);
        todo.setAlarm60min(alarm60min);
        Thread editThread = new Thread(new Runnable() {
            @Override
            public void run() {
                todosDao.insert(todo);
            }
        });
        editThread.start();
        Log.i("ID", String.valueOf(id));
        return id;
    }

    public ArrayList<Todo> getAllNote() {
        final ArrayList<Todo> todoArrayList = new ArrayList<>();
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                todoArrayList.addAll(todosDao.findAllNotes());
            }
        });
        loadThread.start();

        try {
            loadThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return todoArrayList;
    }

    public Todo findNote(final int id) {
        final ArrayList<Todo> todo = new ArrayList<>();
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                todo.add(todosDao.findNotewithID(id));
            }
        });
        loadThread.start();

        try {
            loadThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return todo.get(0);
    }

    public void deleteNote(int id) {
        final Todo todo = findNote(id);
        Thread deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                todosDao.DeleteNotes(todo);
            }
        });
        deleteThread.start();
    }

    public ArrayList<Todo> findAllNotes(final String word) {
        final ArrayList<Todo> todoArrayList = new ArrayList<>();
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                todoArrayList.addAll(todosDao.findNoteswithTitle(word));
            }
        });
        loadThread.start();

        try {
            loadThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return todoArrayList;
    }

    public boolean checkDateValid(int i, Date alarmDate) {
        Calendar today = Calendar.getInstance();
        for (int j = 0; j < i; j++) {
            today.add(Calendar.MINUTE, amountMin[i]);
        }
        if (alarmDate.getTime() - today.getTime().getTime() <= 0) {
            return false;
        }
        return true;
    }

    public boolean scheduleReminder(int noteId) {
        Todo findTodo = findNote(noteId);

        if (findTodo.isAlarm0min()) {
            setWorker(findTodo, 0, noteId);
        }
        if (findTodo.isAlarm10min()) {
            setWorker(findTodo, 10, noteId);
        }
        if (findTodo.isAlarm30min()) {
            setWorker(findTodo, 30,  noteId);
        }
        if (findTodo.isAlarm60min()) {
            setWorker(findTodo, 60, noteId);
        }
        return true;
    }

    public void setWorker(Todo findTodo, int time, int noteId) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MINUTE, time);
        long duration = findTodo.getDateTime().getTime() - today.getTime().getTime();
        Data newdata = new Data.Builder().putString("title", findTodo.getTitle()).putString("desc", findTodo.getDescription()).build();
        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotifyWorker.class)
                .setInitialDelay(duration, TimeUnit.MILLISECONDS)
                .setInputData(newdata)
                .addTag(String.valueOf(noteId))
                .build();
        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(notificationWork);
    }

    public void cancelReminder(int noteId) {
        WorkManager instance = WorkManager.getInstance();
        instance.cancelAllWorkByTag(String.valueOf(noteId));
    }
}
