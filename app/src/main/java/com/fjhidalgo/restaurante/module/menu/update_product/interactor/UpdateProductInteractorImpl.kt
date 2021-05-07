package com.fjhidalgo.restaurante.module.menu.update_product.interactor

import android.net.Uri
import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
import java.net.URI

class UpdateProductInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper): UpdateProductInteractor, BaseInteractorImpl(preferenceHelper, apiHelper)  {

    override fun getFood(typeFood: String, callback: FirebaseCallback) {
        apiHelper.performGetFood(typeFood, callback)
    }

    override fun getDrink(typeDrink: String, callback: FirebaseCallback) {
        apiHelper.performGetDrink(typeDrink, callback)
    }

    override fun updateFood(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate) {
        apiHelper.performUpdateFood(updateProduct, type, uri, callback)
    }

    override fun updateDrink(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate) {
        apiHelper.performUpdateDrink(updateProduct, type, uri, callback)
    }
}