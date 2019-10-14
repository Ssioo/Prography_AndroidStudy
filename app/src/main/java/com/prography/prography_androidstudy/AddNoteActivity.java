package com.prography.prography_androidstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private Toolbar toolbar;

    private boolean FLAG_EDIT_OR_ADD = false;
    private int noteId;

    private NoteDatabase db;
    private NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        /* findViewByID */
        etTitle = findViewById(R.id.title_add);
        etDescription = findViewById(R.id.description_add);
        toolbar = findViewById(R.id.toolbar_add);

        /* NoteDatabase */
        db = NoteDatabase.getDatabase(AddNoteActivity.this);
        noteModel = new NoteModel(this);

        /* Toolbar */
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            FLAG_EDIT_OR_ADD = true;
            Note note = noteModel.getNote(noteId);
            etTitle.setText(note.getTitle());
            etDescription.setText(note.getDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add, menu);
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
                    if (FLAG_EDIT_OR_ADD) {
                        noteModel.addNote(etTitle.getText().toString(), etDescription.getText().toString(), noteId);
                    } else {
                        noteModel.addNote(etTitle.getText().toString(), etDescription.getText().toString());
                    }
                    Toast.makeText(this, "메모 저장에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
