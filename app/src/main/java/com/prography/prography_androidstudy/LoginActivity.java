package com.prography.prography_androidstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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

    private boolean isLoginSet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* findViewById */
        btnLogin = findViewById(R.id.login_login_button);
        btnRegister = findViewById(R.id.login_register_button);
        etEmail = findViewById(R.id.login_email);
        etPw = findViewById(R.id.login_pw);
        chkAutoLogin = findViewById(R.id.chk_autologin);
        chkSaveUserId = findViewById(R.id.chk_saveUserId);

        /* UserModel */
        userModel = new UserModel(this);

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
        // 자동 로그인 확인 기능
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* 버튼 재활성화 */
        btnLogin.setEnabled(true);
        btnRegister.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /* SharedPreferences 내용 저장 */
    }
}
