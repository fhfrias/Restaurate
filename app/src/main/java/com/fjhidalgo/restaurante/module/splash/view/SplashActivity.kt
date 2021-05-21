package com.fjhidalgo.restaurante.module.splash.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.login.view.LoginActivity
import com.fjhidalgo.restaurante.module.main.activity.view.MainActivity
import com.fjhidalgo.restaurante.module.splash.interactor.SplashInteractor
import com.fjhidalgo.restaurante.module.splash.interactor.SplashInteractorImpl
import com.fjhidalgo.restaurante.module.splash.presenter.SplashPresenter
import com.fjhidalgo.restaurante.module.splash.presenter.SplashPresenterImpl
import com.google.gson.Gson





class SplashActivity: AppCompatActivity(), SplashView {

    val presenter: SplashPresenter<SplashView, SplashInteractor> by lazy {
        SplashPresenterImpl(SplashInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        presenter.onAttach(this)

        val extras = intent.extras
        var email: String? = null

        if (extras == null){
            goToLogin()
            finish()
        } else {
            email = extras!!.getString("email", "")
            presenter.getDataUser(email)
        }

    }

    private fun goToLogin(){
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

    private fun goToMain(userModel: UserModel){

        val intentMain = Intent(this, MainActivity::class.java)
        intentMain.putExtra("email", userModel.email)
        intentMain.putExtra("name", userModel.name)
        intentMain.putExtra("surname", userModel.surname)
        intentMain.putExtra("isAdmin", userModel.isAdmin)
        intentMain.putExtra("id", userModel.id)

        startActivity(intentMain)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun gottenDataUser(userModel: UserModel) {
        goToMain(userModel)
        finish()
    }

    override fun errorDataUser() {
        Toast.makeText(this, getString(R.string.error_unknow_splash), Toast.LENGTH_LONG).show()
        goToLogin()
        finish()
    }
}