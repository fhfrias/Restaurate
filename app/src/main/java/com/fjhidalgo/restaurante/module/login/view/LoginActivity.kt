package com.fjhidalgo.restaurante.module.login.view

import android.content.Intent
import android.os.Bundle
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
import com.fjhidalgo.restaurante.module.signup.view.SignupActivity
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

        btnSignup!!.setOnClickListener {

            val intentSignup = Intent(this, SignupActivity::class.java)
            startActivity(intentSignup)
        }

        btnLogin!!.setOnClickListener {
            presenter!!.signinUser(etEmail!!.text.toString(), etPassword!!.text.toString())

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun okLogin() {
        val intentMain = Intent(this, MainActivity::class.java)
        startActivity(intentMain)
    }

    override fun errorLogin() {
        Toast.makeText(this, getString(R.string.login_no_valid_msg), Toast.LENGTH_LONG).show()
    }
}