package com.fjhidalgo.restaurante.module.base.view

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BaseActivityView, FragmentCallback {

    companion object {
        var TAG = BaseActivity::class.java.simpleName
    }
}
