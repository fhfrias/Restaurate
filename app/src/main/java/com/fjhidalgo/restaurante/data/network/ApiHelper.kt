package com.fjhidalgo.restaurante.data.network

import android.net.Uri
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
import java.net.URI

interface ApiHelper {

    fun performGetFood(typeFood: String, callback: FirebaseCallback)
    fun performGetDrink(typeDrink: String, callback: FirebaseCallback)
    fun performDeleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun performDeleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun performUpdateFood(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate)
    fun performUpdateDrink(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate)
}
