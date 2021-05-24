package com.fjhidalgo.restaurante.module.recorved_password.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.recorved_password.interactor.RecorvedPassInteractor
import com.fjhidalgo.restaurante.module.recorved_password.view.RecorvedPassView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackRecoveryPass


class RecorvedPassPresenterImpl<V : RecorvedPassView, I : RecorvedPassInteractor>
constructor(interactor: I) : BasePresenterImpl<V, I>(interactor), RecorvedPassPresenter<V, I> {

    override fun recorvedPass(email: String) {

        interactor!!.recorvedPass(email, object : FirebaseCallbackRecoveryPass{

            override fun onResponse() {
                getView()?.recoveryPassOK()
            }

            override fun onError() {
                getView()?.recoveryPassError()
            }
        })
    }
}