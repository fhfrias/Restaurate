package com.fjhidalgo.restaurante.module.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.login.interactor.LoginInteractor
import com.fjhidalgo.restaurante.module.login.interactor.LoginInteractorImpl
import com.fjhidalgo.restaurante.module.login.presenter.LoginPresenter
import com.fjhidalgo.restaurante.module.login.presenter.LoginPresenterImpl
import com.fjhidalgo.restaurante.module.main.activity.view.MainActivity
import com.fjhidalgo.restaurante.module.recorved_password.view.RecorvedPassActivity
import com.fjhidalgo.restaurante.module.signup.view.SignupActivity
import com.fjhidalgo.restaurante.module.splash.view.SplashActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity: AppCompatActivity(), LoginView {

    private val presenter: LoginPresenter<LoginView, LoginInteractor> by lazy {
        LoginPresenterImpl<LoginView, LoginInteractor>(LoginInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    private var etEmail: TextInputEditText? = null
    private var etPassword: TextInputEditText? = null
    private var btnLogin: MaterialButton? = null
    private var btnSignup: MaterialButton? = null
    private var emailLogin: String? = null
    private var forgotPassword: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        presenter.onAttach(this)

        initView()

    }

    private fun initView(){
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)
        forgotPassword = findViewById(R.id.tvForgotPassword)

        btnSignup!!.setOnClickListener {

            val intentSignup = Intent(this, SignupActivity::class.java)
            startActivity(intentSignup)
        }

        btnLogin!!.setOnClickListener {
            if (etEmail!!.text.toString().equals("") || etPassword!!.text.toString().equals("")){
                Toast.makeText(this, getString(R.string.must_complete_fields_login), Toast.LENGTH_LONG).show()
            } else {
                emailLogin = etEmail!!.text.toString()
                presenter!!.signinUser(etEmail!!.text.toString(), etPassword!!.text.toString())
            }
        }

        forgotPassword!!.setOnClickListener {
            val intentRecorved = Intent(this, RecorvedPassActivity::class.java)
            startActivity(intentRecorved)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun okLogin() {
        val intentMain = Intent(this, SplashActivity::class.java)
        intentMain.putExtra("email", emailLogin)
        startActivity(intentMain)
        finish()
    }

    override fun errorLogin() {
        emailLogin = null
        Toast.makeText(this, getString(R.string.login_no_valid_msg), Toast.LENGTH_LONG).show()
    }
}