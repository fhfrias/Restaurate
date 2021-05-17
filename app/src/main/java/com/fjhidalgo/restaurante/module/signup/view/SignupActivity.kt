package com.fjhidalgo.restaurante.module.signup.view

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.signup.interactor.SignupInteractor
import com.fjhidalgo.restaurante.module.signup.interactor.SignupInteractorImpl
import com.fjhidalgo.restaurante.module.signup.presenter.SignupPresenter
import com.fjhidalgo.restaurante.module.signup.presenter.SignupPresenterImpl
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class SignupActivity: AppCompatActivity(), SignupView {

    private var etEmail: TextInputEditText? = null
    private var etName: TextInputEditText? = null
    private var etSurname: TextInputEditText? = null
    private var etPass: TextInputEditText? = null
    private var etRepeatPass: TextInputEditText? = null
    private var btnSigup: MaterialButton? = null
    private var appBarLayout: CustomAppBarLayout? = null

    val presenter: SignupPresenter<SignupView, SignupInteractor> by lazy {
        SignupPresenterImpl(SignupInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl()))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)
        presenter.onAttach(this)

        initView()
        setUpAppBarLayout()
    }

    private fun initView() {
        etEmail = findViewById(R.id.etEmail)
        etName = findViewById(R.id.etName)
        etSurname = findViewById(R.id.etSurname)
        etPass = findViewById(R.id.etPassword)
        etRepeatPass = findViewById(R.id.etRepeatPassword)
        btnSigup = findViewById(R.id.btnSignup)
        appBarLayout = findViewById(R.id.appPartnerBarLayout)

        btnSigup!!.setOnClickListener {
            btnSigup!!.isEnabled = false
            if(isCorrect()){
                val newUser = UserModel(etEmail!!.text.toString(), etName!!.text!!.toString(), etSurname!!.text.toString(), false, UUID.randomUUID().toString())
                presenter.createUser(etEmail!!.text!!.toString(), etPass!!.text!!.toString(), newUser)
            } else {
                btnSigup!!.isEnabled = true
            }
        }
    }

    private fun setUpAppBarLayout() {
        setSupportActionBar(appBarLayout?.toolbar)
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = getString(R.string.signup_title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun isCorrect(): Boolean {

        var isCorrectBoolean = true

        //Email
        if (etEmail!!.text!!.length == 0 || !Patterns.EMAIL_ADDRESS.matcher(etEmail!!.text!!.toString()).matches()){
            etEmail!!.setError(getString(R.string.txt_email_novalid))
            isCorrectBoolean = false
        }
        //Name
        if (etName!!.text!!.length < 2){
            etName!!.setError(getString(R.string.txt_name_novalid))
            isCorrectBoolean = false
        }
        //Surname
        if (etSurname!!.text!!.length < 2){
            etSurname!!.setError(getString(R.string.txt_surname_novalid))
            isCorrectBoolean = false
        }
        //Pass
        if( etPass!!.text.toString().equals(etRepeatPass!!.text!!.toString()) && etPass!!.text.toString().length >= 6){

        } else {
            etPass!!.setError(getString(R.string.txt_pass_novalid))
            etRepeatPass!!.setError(getString(R.string.txt_pass_novalid))
            isCorrectBoolean = false
        }

        return isCorrectBoolean
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun okCreateUser() {

        Toast.makeText(this, getString(R.string.signup_user_ok), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun errorCreateUser() {
        Toast.makeText(this, getString(R.string.signup_user_error), Toast.LENGTH_LONG).show()
        btnSigup!!.isEnabled = true
    }
}