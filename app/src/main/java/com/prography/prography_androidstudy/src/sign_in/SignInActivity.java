package com.prography.prography_androidstudy.src.sign_in;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.prography.prography_androidstudy.R;
import com.prography.prography_androidstudy.Room.UserModel;
import com.prography.prography_androidstudy.src.BaseActivity;
import com.prography.prography_androidstudy.src.sign_in.interfaces.SignInActivityView;
import com.prography.prography_androidstudy.src.sign_up.SignUpActivity;
import com.prography.prography_androidstudy.src.main.MainActivity;

public class SignInActivity extends BaseActivity implements SignInActivityView {

    private static final boolean DEFAULT_AUTO_LOGIN = false;
    private static final String DEFAULT_SAVED_USERID = "";

    private TextView btnLogin;
    private TextView btnRegister;
    private EditText etEmail;
    private EditText etPw;
    private CheckBox chkAutoLogin;
    private CheckBox chkSaveUserId;

    private UserModel userModel;
    private SharedPreferences sharedPreferences;

    private boolean isLoginEnqueued = false;

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

        /* Shared Preferences */
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        /* UserModel */
        userModel = new UserModel(this);

        /* Set On Checked Changed Listener */
        chkAutoLogin.setOnCheckedChangeListener(this); // Auto Login

        /* Set On Click Listener */
        btnLogin.setOnClickListener(this); // Login
        btnRegister.setOnClickListener(this); // Register
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 자동 로그인 확인 기능

        boolean isAutoLogin = sharedPreferences.getBoolean("autoLogin", DEFAULT_AUTO_LOGIN);
        String savedUserId = sharedPreferences.getString("userId", DEFAULT_SAVED_USERID);
        if (isAutoLogin) {
            chkAutoLogin.setChecked(true);
            startNextActivity(MainActivity.class);
            finish();
        }

        /* 아이디 자동 완성 */
        if (!savedUserId.equals(DEFAULT_SAVED_USERID)) {
            chkSaveUserId.setChecked(true);
            etEmail.setText(savedUserId);
        }
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
        if (isLoginEnqueued) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (chkSaveUserId.isChecked()) {
                editor.putString("userId", etEmail.getText().toString());
            } else {
                editor.remove("userId");
            }
            editor.putBoolean("autoLogin", chkAutoLogin.isChecked());
            editor.apply();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_button:
                /* 버튼 오작동 방지 일시 disable */
                btnLogin.setEnabled(false);

                String email_string = etEmail.getText().toString();
                String pw_string = etPw.getText().toString();
                /* email, pw DB 작업*/
                if (userModel.checkLogin(email_string, pw_string)) {
                    isLoginEnqueued = true;
                    startNextActivity(MainActivity.class);
                    finish();
                } else {
                    makeNewToast("아이디 또는 비밀번호를 확인해 주세요.");
                    /* 실패시 버튼 재활성화 */
                    btnLogin.setEnabled(true);
                }
                break;

            case R.id.login_register_button:
                /* 버튼 일시 비활성화 */
                btnRegister.setEnabled(false);
                startNextActivity(SignUpActivity.class);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.chk_autologin:
                chkSaveUserId.setChecked(isChecked);
                chkAutoLogin.setEnabled(!isChecked);
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
