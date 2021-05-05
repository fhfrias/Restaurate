package com.fjhidalgo.restaurante.data.network

import com.fjhidalgo.restaurante.data.network.module.product.ProductImpl
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete

class ApiHelperImpl : ApiHelper {

    override fun performGetFood(typeFood: String, callback: FirebaseCallback) {
        val productImpl = ProductImpl()
        productImpl.getListFood(typeFood, callback)
    }

    override fun performGetDrink(typeDrink: String, callback: FirebaseCallback) {
        val productImpl = ProductImpl()
        productImpl.getListDrink(typeDrink, callback)
    }

    override fun performDeleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete) {
        val productImpl = ProductImpl()
        productImpl.deleteFood(id, typeFood, linkImage, callback)
    }

    override fun performDeleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete) {
        val productImpl = ProductImpl()
        productImpl.deleteDrink(id, typeDrink, linkImage, callback)
    }
}
