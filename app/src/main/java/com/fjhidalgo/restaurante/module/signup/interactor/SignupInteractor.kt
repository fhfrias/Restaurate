package com.fjhidalgo.restaurante.module.signup.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignup

interface SignupInteractor: BaseInteractor {
    fun createUser(email: String, password: String, userModel: UserModel, callback: FirebaseCallbackSignup)
}