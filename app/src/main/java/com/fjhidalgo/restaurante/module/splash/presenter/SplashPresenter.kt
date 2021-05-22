package com.fjhidalgo.restaurante.module.splash.presenter

import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter

interface SplashPresenter<V,I>: BasePresenter<V, I> {

    fun getDataUser(email: String)
    fun getVersion()
}