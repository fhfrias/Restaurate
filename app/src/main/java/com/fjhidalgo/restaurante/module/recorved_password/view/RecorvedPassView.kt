package com.fjhidalgo.restaurante.module.recorved_password.view

import com.fjhidalgo.restaurante.module.base.view.BaseView

interface RecorvedPassView: BaseView {

    fun recoveryPassOK()
    fun recoveryPassError()
}