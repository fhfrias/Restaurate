package com.fjhidalgo.restaurante.module.signup.view

import com.fjhidalgo.restaurante.module.base.view.BaseView

interface SignupView: BaseView {
    fun okCreateUser()
    fun errorCreateUser()
}