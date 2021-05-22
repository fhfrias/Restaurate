package com.fjhidalgo.restaurante.module.menu.users.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate

interface UsersInteractor: BaseInteractor {

    fun getUsers(callbackDataUser: FirebaseCallbackDataUser)
    fun updateIsAdmin(id: String, isAdmin: Boolean, callbackUpdate: FirebaseCallbackUpdate)
}