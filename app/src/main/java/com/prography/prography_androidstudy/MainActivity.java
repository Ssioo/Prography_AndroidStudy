package com.prography.prography_androidstudy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NoteModel noteModel;
    private TextView tvLogout;
    private RecyclerView rvNoteList;
    private FloatingActionButton fbtnAddNote;
    private DrawerLayout dlMain;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private SearchView svMain;
    private ArrayList<Note> noteArrayList;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivityState", "OnCreate");

        /* findViewById */
        tvLogout = findViewById(R.id.btn_main_logout);
        toolbar = findViewById(R.id.toolbar_main);
        rvNoteList = findViewById(R.id.note_list_main);
        fbtnAddNote = findViewById(R.id.fbtn_add_memo);
        dlMain = findViewById(R.id.drawer_main);

        /* NoteDatabase */
        noteModel = new NoteModel(this);

        /* Toolbar */
        toolbar.setTitle("모든 노트");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        /* RecyclerView - NoteList */
        rvNoteList.setLayoutManager(new LinearLayoutManager(this));

        /* Set on Click Listener */
        fbtnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                /* 자동 로그인 해제 */
                editor.putBoolean("autoLogin", false);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        noteArrayList = noteModel.getAllNote();
        noteAdapter = new NoteAdapter(noteArrayList, MainActivity.this);
        rvNoteList.setAdapter(noteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        svMain = (SearchView) menu.findItem(R.id.action_search).getActionView();
        svMain.setMaxWidth(Integer.MAX_VALUE);
        svMain.setQueryHint("일정 이름으로 검색하기");
        svMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                noteArrayList = noteModel.getNote('%' + query + '%');
                noteAdapter = new NoteAdapter(noteArrayList, MainActivity.this);
                rvNoteList.setAdapter(noteAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteArrayList = noteModel.getNote('%' + newText + '%');
                noteAdapter = new NoteAdapter(noteArrayList, MainActivity.this);
                rvNoteList.setAdapter(noteAdapter);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dlMain.openDrawer(GravityCompat.START);
                break;
            case R.id.action_edit:
                break;
            case R.id.action_sort:
                break;
            case R.id.action_show:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (dlMain.isDrawerOpen(GravityCompat.START)) {
            dlMain.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
