package com.fjhidalgo.restaurante.module.base.view

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fjhidalgo.restaurante.R

abstract class BasePreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val theme: Resources.Theme = getTheme()
        theme.applyStyle(R.style.Theme_Restaurante, true)
        setTheme(theme)
        super.onCreate(savedInstanceState)
    }
}
