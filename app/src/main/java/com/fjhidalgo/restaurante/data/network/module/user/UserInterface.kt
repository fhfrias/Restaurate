package com.fjhidalgo.restaurante.data.network.module.user

import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackVersion

interface UserInterface {
    fun getDataUser(email: String, callback: FirebaseCallbackDataUser)
    fun updateIsAdminUser(id: String, isAdmin: Boolean, callback: FirebaseCallbackUpdate)
    fun getVersion(callbackVersion: FirebaseCallbackVersion)
}