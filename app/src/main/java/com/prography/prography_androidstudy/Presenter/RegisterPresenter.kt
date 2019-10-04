package com.prography.prography_androidstudy.Presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.prography.prography_androidstudy.Contract.RegisterContract
import com.prography.prography_androidstudy.Model.UserModel
import com.prography.prography_androidstudy.View.LoginActivity

class RegisterPresenter(private val view: RegisterContract.View, private val context: Context, private val activity: Activity) : RegisterContract.Presenter {
    private val userModel: UserModel = UserModel(context)

    override fun presenterView() {
        view.setView()
    }

    override fun signUp(email: String, pw: String, pwCheck: String) {
        when (userModel.signUp(email, pw, pwCheck)) {
            "Success" -> {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK))
                activity.finish()
            }
            "NotChecked" -> {
                view.showToast("비밀번호와 비밀번호 확인이 일치하지 않습니다")
            }
            else -> {
                view.showToast("동일한 이메일의 계정이 존재합니다")
            }
        }
    }
}