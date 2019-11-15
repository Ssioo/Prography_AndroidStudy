package com.prography.prography_androidstudy.Room;

import android.content.Context;

import java.util.ArrayList;

public class UserModel {
    private UserDatabase db;
    private UserDao userDao;

    public UserModel(Context context) {
        db = UserDatabase.getDatabase(context);
        userDao = db.userDao();
    }

    public boolean checkLogin(final String email, final String pw) {
        final ArrayList<User> userArrayList = new ArrayList<>();
        Thread loginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                userArrayList.addAll(userDao.userLogin(email, pw));
            }
        });
        loginThread.start();

        try {
            loginThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (userArrayList.size() == 0) {
            return false;
        } else {
            return userArrayList.get(0).getEmail().equals(email);
        }
    }

    public String signUp(final String email, String pw, String pwCheck) {
        if (!pw.equals(pwCheck)) {
            return "PasswordError";
        }

        final ArrayList<User> userArrayList = new ArrayList<>();
        Thread signUpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                userArrayList.addAll(userDao.findUser(email));
            }
        });
        signUpThread.start();

        try {
            signUpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* User Data 중복 */
        if (userArrayList.size() != 0) {
            return "RedundantUser";
        }

        final User newUser = new User(email, pw);
        Thread uploadUserThread = new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.insert(newUser);
            }
        });
        uploadUserThread.start();
        return "Success";
    }
}
