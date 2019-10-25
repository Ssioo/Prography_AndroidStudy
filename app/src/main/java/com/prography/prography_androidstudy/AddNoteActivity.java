package com.prography.prography_androidstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etTitle;
    private EditText etDescription;
    private TextView tvDate;
    private TextView tvTime;
    private Toolbar toolbar;

    private boolean FLAG_EDIT_OR_ADD = false;
    private int noteId;
    private Calendar defaultCalendar;

    private NoteModel noteModel;

    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault());
    private SimpleDateFormat sdfTime = new SimpleDateFormat("a HH시 mm분", Locale.getDefault());
    private SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy년 M월 d일 a HH시 mm분", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        /* findViewByID */
        etTitle = findViewById(R.id.title_add);
        etDescription = findViewById(R.id.description_add);
        toolbar = findViewById(R.id.toolbar_add);
        tvDate = findViewById(R.id.date_add);
        tvTime = findViewById(R.id.time_add);

        /* NoteDatabase */
        noteModel = new NoteModel(this);

        /* Toolbar */
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();


        /* Initiailzing */
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            FLAG_EDIT_OR_ADD = true;

            Note note = noteModel.getNote(noteId);
            etTitle.setText(note.getTitle());
            etDescription.setText(note.getDescription());
            tvDate.setText(note.getDate());
            tvTime.setText(note.getTime());
            defaultCalendar = Calendar.getInstance();
            try {
                defaultCalendar.setTime(sdfDateTime.parse(note.getDate() + " " + note.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            defaultCalendar = Calendar.getInstance();
        }

        /* Set on Click Listenter */
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add, menu);
        if (FLAG_EDIT_OR_ADD) {
            menu.findItem(R.id.action_delete).setVisible(true);
        } else {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (etTitle.getText().toString().equals("") || etDescription.getText().toString().equals("")) {
                    Toast.makeText(this, "입력한 내용이 없어 메모를 저장하지 못했어요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String noteTitle = etTitle.getText().toString();
                    String noteDescription = etDescription.getText().toString();
                    String noteDate = sdfDate.format(defaultCalendar.getTime());
                    String noteTime = sdfTime.format(defaultCalendar.getTime());
                    if (FLAG_EDIT_OR_ADD) {
                        noteModel.addNote(noteTitle, noteDescription, noteDate, noteTime, noteId);
                    } else {
                        noteModel.addNote(noteTitle, noteDescription, noteDate, noteTime);
                    }
                    Toast.makeText(this, "메모 저장에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;

            case R.id.action_delete:
                // AlertDialog
                noteModel.deleteNote(noteId);
                Toast.makeText(this, "메모 삭제에 성공했습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_add:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        defaultCalendar.set(Calendar.YEAR, year);
                        defaultCalendar.set(Calendar.MONTH, month);
                        defaultCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        tvDate.setText(sdfDate.format(defaultCalendar.getTime()));
                    }
                }, defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH), defaultCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;

            case R.id.time_add:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        defaultCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        defaultCalendar.set(Calendar.MINUTE, minute);
                        tvTime.setText(sdfTime.format(defaultCalendar.getTime()));
                    }
                }, defaultCalendar.get(Calendar.HOUR_OF_DAY), defaultCalendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
                break;
        }
    }
}
