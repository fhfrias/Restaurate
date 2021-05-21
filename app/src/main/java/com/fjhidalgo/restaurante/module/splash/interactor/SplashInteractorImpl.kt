package com.fjhidalgo.restaurante.module.splash.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.module.signup.interactor.SignupInteractor
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser

class SplashInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper): SplashInteractor, BaseInteractorImpl(preferenceHelper, apiHelper) {

    override fun getDataUser(email: String, callback: FirebaseCallbackDataUser) {
        apiHelper!!.performGetDataUser(email, callback)
    }
}