package com.prography.prography_androidstudy.src.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prography.prography_androidstudy.Room.Todo;
import com.prography.prography_androidstudy.src.common.utils.ItemTouchHelperCallback;
import com.prography.prography_androidstudy.src.main.adapter.TodoAdapter;
import com.prography.prography_androidstudy.R;
import com.prography.prography_androidstudy.Room.TodoModel;
import com.prography.prography_androidstudy.src.add_edit.AddEditActivity;
import com.prography.prography_androidstudy.src.BaseActivity;
import com.prography.prography_androidstudy.src.main.interfaces.MainActivityView;
import com.prography.prography_androidstudy.src.sign_in.SignInActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MainActivityView {

    public boolean deletemode = false;
    private TodoModel todoModel;
    private TextView tvLogout;
    private RecyclerView rvNoteList;
    private FloatingActionButton fbtnAddNote;
    private DrawerLayout dlMain;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private SearchView svMain;
    private ArrayList<Todo> todoArrayList;
    private TodoAdapter todoAdapter;

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


        /* TodoDatabase */
        todoModel = new TodoModel(this);

        /* Toolbar */
        toolbar.setTitle("모든 노트");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        /* RecyclerView - NoteList */
        rvNoteList.setLayoutManager(new LinearLayoutManager(this));

        /* Set on Click Listener */
        fbtnAddNote.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        todoArrayList = todoModel.getAllNote();
        todoAdapter = new TodoAdapter(todoArrayList, MainActivity.this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(todoAdapter));
        itemTouchHelper.attachToRecyclerView(rvNoteList);
        rvNoteList.setAdapter(todoAdapter);
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
                todoArrayList = todoModel.findAllNotes('%' + query + '%');
                todoAdapter = new TodoAdapter(todoArrayList, MainActivity.this);
                rvNoteList.setAdapter(todoAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                todoArrayList = todoModel.findAllNotes('%' + newText + '%');
                todoAdapter = new TodoAdapter(todoArrayList, MainActivity.this);
                rvNoteList.setAdapter(todoAdapter);
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
                if (deletemode) {
                    item.setTitle("편집");
                } else {
                    item.setTitle("삭제");
                }
                deletemode = !deletemode;
                todoAdapter.setDeleteMode(deletemode);
                todoAdapter.notifyDataSetChanged();
                break;

            case R.id.action_sort:
                break;

            case R.id.action_show:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (dlMain.isDrawerOpen(GravityCompat.START)) {
            dlMain.closeDrawers();
        } else if (deletemode) {
            deletemode = false;
            todoAdapter.setDeleteMode(deletemode);
            todoAdapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbtn_add_memo:
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_main_logout:
                sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                /* 자동 로그인 해제 */
                editor.putBoolean("autoLogin", false);
                editor.apply();

                startNextActivity(SignInActivity.class);
                finish();
                break;
        }
    }
}
