package com.prography.prography_androidstudy.Contract

interface LoginContract {
    interface View {
        fun setView()
        fun showToast(title:String)
    }

    interface Presenter {
        fun presenterView()
        fun Login(email: String, pw: String)
    }
}