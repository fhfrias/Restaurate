package com.fjhidalgo.restaurante.module.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.module.login.view.LoginActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        val handler = Handler()
        handler.postDelayed(Runnable {
            handler.removeCallbacksAndMessages(null)

            goToLogin()

        }, 3000)

    }

    private fun goToLogin(){
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }
}