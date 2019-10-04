package com.prography.prography_androidstudy.Contract

interface RegisterContract {
    interface View {
        fun setView()
        fun showToast(title: String)
    }

    interface Presenter {
        fun presenterView()
        fun signUp(email: String, pw: String, pwCheck: String)
    }
}