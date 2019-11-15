package com.prography.prography_androidstudy.src.sign_up;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.prography.prography_androidstudy.R;
import com.prography.prography_androidstudy.Room.UserDatabase;
import com.prography.prography_androidstudy.Room.UserModel;
import com.prography.prography_androidstudy.src.BaseActivity;
import com.prography.prography_androidstudy.src.sign_up.interfaces.SignUpActivityView;


public class SignUpActivity extends BaseActivity implements SignUpActivityView {
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
        userModel = new UserModel(this);

        /* Set On Click Listener */
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_register_button:
                String email_string = etEmail.getText().toString();
                String pw_string = etPw.getText().toString();
                String pwCheck_string = etPwCheck.getText().toString();

                switch (userModel.signUp(email_string, pw_string, pwCheck_string)) {
                    case "PasswordError":
                        makeNewToast("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                        etEmail.setText("");
                        etPw.setText("");
                        etPwCheck.setText("");
                        break;

                    case "RedundantUser":
                        makeNewToast("동일한 이메일의 계정이 존재합니다.");
                        break;

                    case "Success":
                        makeNewToast("계정 생성에 성공하였습니다.");
                        btnRegister.setText("로그인 화면으로 돌아가기");
                        btnRegister.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish(); // onDestroy
                            }
                        });
                        break;
                }
                break;
        }
    }

    @Override
    public void validateSuccess(int uniqueId) {

    }

    @Override
    public void validateFalire(String message) {

    }
}
