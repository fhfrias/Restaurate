package com.fjhidalgo.restaurante.module.recorved_password.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter

interface RecorvedPassPresenter<V, I> : BasePresenter<V, I> {

    fun recorvedPass(email: String)
}