package com.fjhidalgo.restaurante.module.login.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignin


interface LoginPresenter<V, I> : BasePresenter<V, I> {

    fun signinUser(email: String, password: String)
}