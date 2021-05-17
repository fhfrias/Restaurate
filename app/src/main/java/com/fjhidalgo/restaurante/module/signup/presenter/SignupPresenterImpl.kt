package com.fjhidalgo.restaurante.module.signup.presenter

import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.signup.interactor.SignupInteractor
import com.fjhidalgo.restaurante.module.signup.view.SignupView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignup

class SignupPresenterImpl<V: SignupView, I: SignupInteractor>(interactor:I): BasePresenterImpl<V, I>(interactor),
    SignupPresenter<V, I> {
    override fun createUser(email: String, password: String, userModel: UserModel) {
        interactor!!.createUser(email, password, userModel, object : FirebaseCallbackSignup{

            override fun onResponse() {
                getView()?.okCreateUser()
            }

            override fun onError() {
                getView()?.errorCreateUser()
            }
        })
    }
}