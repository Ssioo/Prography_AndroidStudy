package com.prography.prography_androidstudy.Presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.prography.prography_androidstudy.Contract.LoginContract
import com.prography.prography_androidstudy.View.MainActivity
import com.prography.prography_androidstudy.Model.UserModel

class LoginPresenter(private val view: LoginContract.View, private val context: Context, private val activity: Activity) : LoginContract.Presenter {
    private val userModel: UserModel = UserModel(context)

    override fun presenterView() {
        view.setView()
    }

    override fun Login(email: String, pw: String) {
        val checkLogin = userModel.checkLogin(email, pw)

        when (checkLogin) {
            true -> {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK))
                activity.finish()
            }
            else -> {
                view.showToast("일치하는 계정이 없습니다")
            }
        }
    }
}