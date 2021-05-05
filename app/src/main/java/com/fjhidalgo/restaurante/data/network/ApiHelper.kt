package com.fjhidalgo.restaurante.data.network

import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete

interface ApiHelper {

    fun performGetFood(typeFood: String, callback: FirebaseCallback)
    fun performGetDrink(typeDrink: String, callback: FirebaseCallback)
    fun performDeleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun performDeleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete)
}
