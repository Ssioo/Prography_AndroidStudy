package com.prography.prography_androidstudy.Contract

interface MainContract {
    interface View {
        fun setView()
    }

    interface Presenter {
        fun presenterView()
    }
}