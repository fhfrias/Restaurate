package com.fjhidalgo.restaurante.module.login.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignin

interface LoginInteractor: BaseInteractor {
    fun signinUser(email: String, password: String, callback: FirebaseCallbackSignin)
}