package com.fjhidalgo.restaurante.module.menu.update_product.interactor

import android.net.Uri
import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
import java.net.URI

interface UpdateProductInteractor: BaseInteractor {
    fun getFood(typeFood: String, callback: FirebaseCallback)
    fun getDrink(typeDrink: String, callback: FirebaseCallback)
    fun updateFood(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate)
    fun updateDrink(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate)
}