package com.prography.prography_androidstudy.Model

import android.content.Context
import com.prography.prography_androidstudy.Room.User
import com.prography.prography_androidstudy.Room.UserDao
import com.prography.prography_androidstudy.Room.UserDatabase

class UserModel(context: Context) {
    private var database: UserDatabase = UserDatabase.getInstance(context)
    private var userDao: UserDao

    init {
        userDao = database.userDao
    }

    fun checkLogin(email: String, pw: String): Boolean {
        val userList = ArrayList<User>()
        val loginThread = Thread {
            userList.addAll(userDao.userLogin(email, pw))
        }
        loginThread.start();

        try {
            loginThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return if (userList.size == 0) {
            false
        } else userList[0].email == email
    }


    fun signUp(email: String, pw: String, pwCheck: String): String {
        val userList = ArrayList<User>()
        val signUpThread = Thread {
            userList.addAll(userDao.findUser(email))
        }
        signUpThread.start()

        try {

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        if (userList.size != 0) {
            return "Already"
        }

        if (pw != pwCheck) {
            return "NotChecked"
        }

        val user = User(email, pw)
        Thread {
            database.userDao.insert(user)
        }.start()

        return "Success"
    }

}