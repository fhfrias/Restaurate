package com.fjhidalgo.restaurante.module.login.view

import com.fjhidalgo.restaurante.module.base.view.BaseView

interface LoginView: BaseView {
    fun okLogin()
    fun errorLogin()
}