package com.fjhidalgo.restaurante.module.splash.presenter

import android.util.Log
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.model.Auth
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.splash.interactor.SplashInteractor
import com.fjhidalgo.restaurante.module.splash.view.SplashView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackVersion
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError



class SplashPresenterImpl<V: SplashView, I: SplashInteractor>(interactor:I): BasePresenterImpl<V, I>(interactor),
    SplashPresenter<V, I> {

    override fun getDataUser(email: String) {
        interactor!!.getDataUser(email, object : FirebaseCallbackDataUser{
            override fun onResponse(dataSnapshot: DataSnapshot) {
                var myUser: UserModel? = null
                var users: List<UserModel>? = null
                dataSnapshot.let {
                    users = dataSnapshot.children.map { snapShot ->
                        snapShot.getValue(UserModel::class.java)!!
                    }
                }
                for (user in users!!){

                    if (user.email == email){
                        myUser = user
                        getView()?.gottenDataUser(myUser)
                        break
                    }
                }

                if (myUser == null){
                    getView()?.errorDataUser()
                }

            }

            override fun onError() {
                getView()?.errorDataUser()
            }
        })
    }

    override fun getVersion() {
        interactor!!.getVersion(object : FirebaseCallbackVersion{

            override fun onResponse(version: Int) {
                getView()?.getVersionOK(version)
            }

            override fun onError() {
                getView()?.getVersionError()
            }
        })
    }
}