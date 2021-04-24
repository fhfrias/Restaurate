package com.fjhidalgo.restaurante.core.app.presenter

import com.fjhidalgo.restaurante.core.app.interactor.AppInteractor
import com.fjhidalgo.restaurante.core.app.view.AppView
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter

interface AppPresenter<V : AppView, I : AppInteractor>
    : BasePresenter<V, I> {

}