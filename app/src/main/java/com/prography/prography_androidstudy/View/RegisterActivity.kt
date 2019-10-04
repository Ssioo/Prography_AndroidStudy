package com.prography.prography_androidstudy.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.prography.prography_androidstudy.Contract.RegisterContract
import com.prography.prography_androidstudy.Presenter.RegisterPresenter
import com.prography.prography_androidstudy.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View, View.OnClickListener {
    private var presenter: RegisterContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this@RegisterActivity, applicationContext, this)
        presenter!!.presenterView()
    }

    override fun setView() {
        register_register_button!!.setOnClickListener(this)
    }

    override fun showToast(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.register_register_button -> {
                val email = register_email.text.toString()
                val pw = register_pw.text.toString()
                val pwCheck = register_pwCheck.text.toString()
                presenter!!.signUp(email, pw, pwCheck)
            }
        }
    }
}
