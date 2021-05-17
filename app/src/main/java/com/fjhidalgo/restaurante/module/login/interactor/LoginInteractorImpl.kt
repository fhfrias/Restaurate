package com.fjhidalgo.restaurante.module.login.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignin

class LoginInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractorImpl(preferenceHelper, apiHelper), LoginInteractor {

    override fun signinUser(email: String, password: String, callback: FirebaseCallbackSignin) {
        apiHelper!!.performSigninUser(email, password, callback)
    }
}