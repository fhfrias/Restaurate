package com.fjhidalgo.restaurante.data.network.module.product

import android.net.Uri
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
import java.net.URI

interface ProductInterface {

    fun getListFood(typeFood: String, callback: FirebaseCallback)
    fun getListDrink(typeDrink: String, callback: FirebaseCallback)
    fun deleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun deleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun updateFood(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate)
    fun updateDrink(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate)
}