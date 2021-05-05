package com.fjhidalgo.restaurante.data.network.module.product

import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete

interface ProductInterface {

    fun getListFood(typeFood: String, callback: FirebaseCallback)
    fun getListDrink(typeDrink: String, callback: FirebaseCallback)
    fun deleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun deleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete)
}