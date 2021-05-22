package com.fjhidalgo.restaurante.module.splash.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackVersion

interface SplashInteractor: BaseInteractor {
    fun getDataUser(email: String, callback: FirebaseCallbackDataUser)
    fun getVersion(callbackVersion: FirebaseCallbackVersion)
}