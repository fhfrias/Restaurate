package com.fjhidalgo.restaurante.core.app.presenter

import com.fjhidalgo.restaurante.core.app.interactor.AppInteractor
import com.fjhidalgo.restaurante.core.app.view.AppView
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl

class AppPresenterImpl<V : AppView, I : AppInteractor>(interactor: I)
    : BasePresenterImpl<V, I>(interactor), AppPresenter<V, I> {

    companion object {
        val TAG: String = AppPresenterImpl::class.java.simpleName
    }


}