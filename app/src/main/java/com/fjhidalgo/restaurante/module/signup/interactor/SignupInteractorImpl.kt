package com.fjhidalgo.restaurante.module.signup.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignup

class SignupInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper): SignupInteractor, BaseInteractorImpl(preferenceHelper, apiHelper) {
    override fun createUser(email: String, password: String, userModel: UserModel, callback: FirebaseCallbackSignup) {
        apiHelper.performCreateUser(email, password, userModel, callback)
    }
}