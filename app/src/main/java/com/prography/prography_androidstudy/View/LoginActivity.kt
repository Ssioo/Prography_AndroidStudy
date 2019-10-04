package com.prography.prography_androidstudy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.prography.prography_androidstudy.Contract.LoginContract
import com.prography.prography_androidstudy.Presenter.LoginPresenter
import com.prography.prography_androidstudy.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View, View.OnClickListener {
    private var presenter: LoginContract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this@LoginActivity, applicationContext, this)
        presenter!!.presenterView()
    }

    override fun setView() {
        login_login_button!!.setOnClickListener(this)
        login_register_button!!.setOnClickListener(this)
    }

    override fun showToast(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_login_button -> {
                val email = login_email.text.toString()
                val pw = login_pw.text.toString()
            }
            R.id.login_register_button -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
