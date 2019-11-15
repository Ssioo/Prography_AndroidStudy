package com.prography.prography_androidstudy.src.add_edit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.prography.prography_androidstudy.Room.Todo;
import com.prography.prography_androidstudy.R;
import com.prography.prography_androidstudy.Room.TodoModel;
import com.prography.prography_androidstudy.src.BaseActivity;
import com.prography.prography_androidstudy.src.add_edit.interfaces.AddEditActivityView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditActivity extends BaseActivity implements AddEditActivityView {

    private EditText etTitle;
    private EditText etDescription;
    private TextView tvDate;
    private TextView tvTime;
    private Toolbar toolbar;
    private CheckBox chkAlarm1;
    private CheckBox chkAlarm2;
    private CheckBox chkAlarm3;
    private CheckBox chkAlarm4;

    private boolean EDIT_MODE = false;
    private int noteId;
    private Calendar defaultCalendar;

    private TodoModel todoModel;

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
        chkAlarm1 = findViewById(R.id.chk_alarm_1);
        chkAlarm2 = findViewById(R.id.chk_alarm_2);
        chkAlarm3 = findViewById(R.id.chk_alarm_3);
        chkAlarm4 = findViewById(R.id.chk_alarm_4);

        /* TodoDatabase */
        todoModel = new TodoModel(this);

        /* Toolbar */
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        /* Initiailzing */
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        defaultCalendar = Calendar.getInstance();
        defaultCalendar.set(Calendar.MILLISECOND, 0);
        tvDate.setText(sdfDate.format(defaultCalendar.getTime()));
        tvTime.setText(sdfTime.format(defaultCalendar.getTime()));
        if (noteId != -1) {
            EDIT_MODE = true;
            Todo todo = todoModel.findNote(noteId);
            etTitle.setText(todo.getTitle());
            etDescription.setText(todo.getDescription());
            tvDate.setText(sdfDate.format(todo.getDateTime()));
            tvTime.setText(sdfTime.format(todo.getDateTime()));
            defaultCalendar.setTime(todo.getDateTime());
        }

        /* Set on Click Listenter */
        tvDate.setOnClickListener(this); // DatePicker
        tvTime.setOnClickListener(this); // TimePicker

        /* Set on Checked Change Listener*/
        chkAlarm1.setOnCheckedChangeListener(this);
        chkAlarm2.setOnCheckedChangeListener(this);
        chkAlarm3.setOnCheckedChangeListener(this);
        chkAlarm4.setOnCheckedChangeListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add, menu);
        menu.findItem(R.id.action_delete).setVisible(EDIT_MODE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String noteTitle = etTitle.getText().toString();
                String noteDescription = etDescription.getText().toString();

                if (EDIT_MODE) {
                    int id = todoModel.addNote(noteTitle, noteDescription, defaultCalendar.getTime(), chkAlarm1.isChecked(), chkAlarm2.isChecked(), chkAlarm3.isChecked(), chkAlarm4.isChecked(), noteId);
                    if (id != -1) {
                        makeNewToast("메모 수정에 성공했습니다.");
                        // WorkManager Delete & Add
                        todoModel.cancelReminder(id);
                        if (todoModel.scheduleReminder(id)) {
                            makeNewToast("알람 생성에 성공하였습니다.");
                        } else {
                            makeNewToast("알람 생성에 실패하였습니다.");
                        }
                    } else {
                        makeNewToast("입력한 내용이 없어 메모를 저장하지 못했어요.");
                    }
                } else {
                    int id = todoModel.addNote(noteTitle, noteDescription, defaultCalendar.getTime(), chkAlarm1.isChecked(), chkAlarm2.isChecked(), chkAlarm3.isChecked(), chkAlarm4.isChecked());
                    if (id != -1) {
                        makeNewToast("메모 생성에 성공했습니다.");
                        // WorkManager Add
                        if (todoModel.scheduleReminder(id)) {
                            makeNewToast("알람 생성에 성공하였습니다.");
                        } else {
                            makeNewToast("알람 생성에 실패하였습니다.");
                        }
                    } else {
                        makeNewToast("입력한 내용이 없어 메모를 저장하지 못했어요.");
                    }
                }
                finish();
                break;

            case R.id.action_delete:
                // AlertDialog
                new AlertDialog.Builder(this)
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                todoModel.deleteNote(noteId);
                                todoModel.cancelReminder(noteId);
                                makeNewToast("메모 삭제에 성공했습니다.");
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setCancelable(true)
                        .create()
                        .show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_add:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        defaultCalendar.set(year, month, dayOfMonth);
                        tvDate.setText(sdfDate.format(defaultCalendar.getTime()));
                    }
                }, defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH), defaultCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.time_add:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        defaultCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        defaultCalendar.set(Calendar.MINUTE, minute);
                        tvTime.setText(sdfTime.format(defaultCalendar.getTime()));
                    }
                }, defaultCalendar.get(Calendar.HOUR_OF_DAY), defaultCalendar.get(Calendar.MINUTE), false).show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.chk_alarm_1:
                if (isChecked) {
                    if (todoModel.checkDateValid(0, defaultCalendar.getTime())) {
                        makeNewToast("정각 알림을 설정합니다");
                    } else {
                        makeNewToast("정각 알림 설정에 실패하였습니다. 시간을 다시 설정해주세요.");
                        buttonView.setChecked(false);
                    }
                } else {
                    makeNewToast("정각 알림을 취소합니다");
                }
                break;
            case R.id.chk_alarm_2:
                if (isChecked) {
                    if (todoModel.checkDateValid(1, defaultCalendar.getTime())) {
                        makeNewToast("10분 전 알림을 설정합니다");
                    } else {
                        makeNewToast("10분 전 알림 설정에 실패하였습니다. 시간을 다시 설정해주세요.");
                        buttonView.setChecked(false);
                    }
                } else {
                    makeNewToast("10분 전 알림을 취소합니다");
                }
                break;
            case R.id.chk_alarm_3:
                if (isChecked) {
                    if (todoModel.checkDateValid(2, defaultCalendar.getTime())) {
                        makeNewToast("30분 전 알림을 설정합니다");
                    } else {
                        makeNewToast("30분 전 알림 설정에 실패하였습니다. 시간을 다시 설정해주세요.");
                        buttonView.setChecked(false);
                    }
                } else {
                    makeNewToast("30분 전 알림을 취소합니다");
                }
                break;
            case R.id.chk_alarm_4:
                if (isChecked) {
                    if (todoModel.checkDateValid(3, defaultCalendar.getTime())) {
                        makeNewToast("1시간 전 알림을 설정합니다");
                    } else {
                        makeNewToast("1시간 전 알림 설정에 실패하였습니다. 시간을 다시 설정해주세요.");
                        buttonView.setChecked(false);
                    }
                } else {
                    makeNewToast("1시간 전 알림을 취소합니다");
                }
                break;
        }
    }
}
