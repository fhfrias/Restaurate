package com.fjhidalgo.restaurante.module.menu.users.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate

class UsersInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper): UsersInteractor, BaseInteractorImpl(preferenceHelper, apiHelper) {

    override fun getUsers(callbackDataUser: FirebaseCallbackDataUser) {
        apiHelper!!.performGetDataUser("", callbackDataUser)
    }

    override fun updateIsAdmin(
        id: String,
        isAdmin: Boolean,
        callbackUpdate: FirebaseCallbackUpdate
    ) {

        apiHelper!!.performUpdateIsAdmin(id, isAdmin, callbackUpdate)
    }
}