package com.prography.prography_androidstudy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView btnLogin;
    private TextView btnRegister;
    private EditText etEmail;
    private EditText etPw;
    private CheckBox chkAutoLogin;
    private CheckBox chkSaveUserId;

    private UserModel userModel;
    private SharedPreferences sharedPreferences;

    private boolean isLoginSet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i("LoginActivityState", "OnCreate");

        /* findViewById */
        btnLogin = findViewById(R.id.login_login_button);
        btnRegister = findViewById(R.id.login_register_button);
        etEmail = findViewById(R.id.login_email);
        etPw = findViewById(R.id.login_pw);
        chkAutoLogin = findViewById(R.id.chk_autologin);
        chkSaveUserId = findViewById(R.id.chk_saveUserId);

        /* UserModel */
        userModel = new UserModel(this);

        /* 자동로그인 checkbox & 아이디저장 checkbox 종속성 액션 */
        chkAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkSaveUserId.setChecked(true);
                    chkSaveUserId.setEnabled(false);
                } else {
                    chkSaveUserId.setEnabled(true);
                    chkSaveUserId.setChecked(false);
                }
            }
        });

        /* 로그인 버튼 클릭 액션 */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 버튼 오작동 방지 일시 disable */
                btnLogin.setEnabled(false);

                String email_string = etEmail.getText().toString();
                String pw_string = etPw.getText().toString();
                /* email, pw DB 작업*/
                if (userModel.checkLogin(email_string, pw_string)) {
                    isLoginSet = true;
                    /* Activity 전환 - Intent */
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    /* 실패시 버튼 재활성화 */
                    btnLogin.setEnabled(true);
                }
            }
        });

        /* 회원가입 버튼 클릭 액션 */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 버튼 일시 비활성화 */
                btnRegister.setEnabled(false);

                /* RegisterActivity로 전환 */
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LoginActivityState", "OnStart");

        // 자동 로그인 확인 기능
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        boolean isAutoLogin = sharedPreferences.getBoolean("autoLogin", false);
        String savedUserId = sharedPreferences.getString("userId", "");
        if (isAutoLogin) {
            chkAutoLogin.setChecked(true);
            /* 자동 로그인 -> MainActivity로 전환 */
            Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(newIntent);
            finish();
        }

        /* 아이디 자동 완성 */
        if (savedUserId != "") {
            chkSaveUserId.setChecked(true);
            etEmail.setText(savedUserId);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LoginActivityState", "OnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LoginActivityState", "OnResume");
        /* 버튼 재활성화 */
        btnLogin.setEnabled(true);
        btnRegister.setEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LoginActivityState", "OnStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LoginActivityState", "OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LoginActivityState", "OnDestroy");

        /* SharedPreferences 내용 저장 */
        if (isLoginSet) {
            sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (chkSaveUserId.isChecked()) {
                editor.putString("userId", etEmail.getText().toString());
            } else {
                editor.remove("userId");
            }
            if (chkAutoLogin.isChecked()) {
                editor.putBoolean("autoLogin", true);
            } else {
                editor.putBoolean("autoLogin", false);
            }
            editor.apply();
        }
    }
}
