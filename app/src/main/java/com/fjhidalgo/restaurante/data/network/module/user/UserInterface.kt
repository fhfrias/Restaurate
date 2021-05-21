package com.fjhidalgo.restaurante.data.network.module.user

import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser

interface UserInterface {
    fun getDataUser(email: String, callback: FirebaseCallbackDataUser)
}