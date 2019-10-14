package com.prography.prography_androidstudy;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    public UserDatabase db;
    private TextView btnRegister;
    private EditText etEmail;
    private EditText etPw;
    private EditText etPwCheck;
    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* findVIewByID */
        btnRegister = findViewById(R.id.register_register_button);
        etEmail = findViewById(R.id.register_email);
        etPw = findViewById(R.id.register_pw);
        etPwCheck = findViewById(R.id.register_pwCheck);

        /* UserDataBase */
        db = UserDatabase.getDatabase(RegisterActivity.this);
        userModel = new UserModel(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = etEmail.getText().toString();
                String pw_string = etPw.getText().toString();
                String pwCheck_string = etPwCheck.getText().toString();

                /* pw, pwCheck 유효성 검사 */
                if (pw_string.equals(pwCheck_string)) {
                    /* DB에 email, pw 전달 작업 */
                    if (userModel.signUp(email_string, pw_string)) {
                        /* btnRegister 내용 바꾸기 */
                        Toast.makeText(RegisterActivity.this, "계정 생성에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        btnRegister.setText("로그인 화면으로 돌아가기");
                        btnRegister.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish(); // onDestroy
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "동일한 이메일의 계정이 존재합니다.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    etEmail.setText("");
                    etPw.setText("");
                    etPwCheck.setText("");
                }
            }
        });
    }
}
