package com.fjhidalgo.restaurante.module.login.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.login.interactor.LoginInteractor
import com.fjhidalgo.restaurante.module.login.view.LoginView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignin

class LoginPresenterImpl<V : LoginView, I : LoginInteractor>
constructor(interactor: I) : BasePresenterImpl<V, I>(interactor), LoginPresenter<V, I> {

    override fun signinUser(email: String, password: String) {
        interactor!!.signinUser(email, password, object : FirebaseCallbackSignin{
            override fun onResponse() {
                getView()?.okLogin()
            }

            override fun onError() {
                getView()?.errorLogin()
            }
        })
    }
}