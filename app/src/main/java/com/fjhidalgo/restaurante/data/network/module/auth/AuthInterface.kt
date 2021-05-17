package com.fjhidalgo.restaurante.data.network.module.auth

import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignin
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignup

interface AuthInterface {

    fun createUserEmailPass(email: String, password: String, userModel: UserModel, callback: FirebaseCallbackSignup)
    fun createUserDataBase(userModel: UserModel, callback: FirebaseCallbackSignup)
    fun signinUser(email: String, password: String, callback: FirebaseCallbackSignin)
}