package com.fjhidalgo.restaurante.module.splash.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser

interface SplashInteractor: BaseInteractor {
    fun getDataUser(email: String, callback: FirebaseCallbackDataUser)
}