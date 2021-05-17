package com.fjhidalgo.restaurante.module.signup.presenter

import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter

interface SignupPresenter<V,I>: BasePresenter<V, I> {
    fun createUser(email: String, password: String, userModel: UserModel)
}