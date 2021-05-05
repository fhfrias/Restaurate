package com.fjhidalgo.restaurante.module.menu.delete_product.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete

class DeleteProductInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper): DeleteProductInteractor, BaseInteractorImpl(preferenceHelper, apiHelper)  {

    override fun getFood(typeFood: String, callback: FirebaseCallback) {
        apiHelper.performGetFood(typeFood, callback)
    }

    override fun getDrink(typeDrink: String, callback: FirebaseCallback) {
        apiHelper.performGetDrink(typeDrink, callback)
    }

    override fun deleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete) {
        apiHelper.performDeleteFood(id, typeFood, linkImage, callback)
    }

    override fun deleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete) {
        apiHelper.performDeleteDrink(id, typeDrink, linkImage, callback)
    }
}