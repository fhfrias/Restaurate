package com.fjhidalgo.restaurante.module.menu.delete_product.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete

interface DeleteProductInteractor: BaseInteractor {

    fun getFood(typeFood: String, callback: FirebaseCallback)
    fun getDrink(typeDrink: String, callback: FirebaseCallback)
    fun deleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun deleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete)
}