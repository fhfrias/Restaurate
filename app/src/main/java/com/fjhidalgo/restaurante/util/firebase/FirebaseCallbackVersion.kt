package com.fjhidalgo.restaurante.util.firebase

interface FirebaseCallbackVersion {
    fun onResponse(version: Int)
    fun onError()
}