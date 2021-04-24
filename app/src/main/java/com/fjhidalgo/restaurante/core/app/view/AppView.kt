package com.fjhidalgo.restaurante.core.app.view

import com.fjhidalgo.restaurante.data.model.Auth
import com.fjhidalgo.restaurante.module.base.view.BaseView

interface AppView : BaseView {
    fun onAuthorizeError()
    fun onLoginInfoResponseSuccess(auth: Auth)
   // fun tryGetPlates(auth: Auth, callback: VolleyCallback)
}