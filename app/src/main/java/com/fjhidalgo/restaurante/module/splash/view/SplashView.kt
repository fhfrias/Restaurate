package com.fjhidalgo.restaurante.module.splash.view

import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.view.BaseView

interface SplashView: BaseView {
    fun gottenDataUser(userModel: UserModel)
    fun errorDataUser()
}